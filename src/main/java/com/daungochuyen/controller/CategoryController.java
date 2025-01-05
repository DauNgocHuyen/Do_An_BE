package com.daungochuyen.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.daungochuyen.entity.Category;
import com.daungochuyen.entity.Product;
import com.daungochuyen.repository.CartRepository;
import com.daungochuyen.repository.CategoryRepository;
import com.daungochuyen.repository.ProductRepository;
import com.daungochuyen.utils.Constants;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {
	private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	/**
	 * Get All Category
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<?> getAll() {
		List<Category> categories = categoryRepository.findAll()
				.stream().filter(category -> category.getDeleted() != Constants.DELETED).toList();
		return ResponseEntity.ok(categories);
	}
	
	/**
	 * Add new category
	 * @param category
	 * @return
	 * @throws IOException 
	 */
	@PostMapping()
	public ResponseEntity<?> createNew(
			@RequestParam("name") String name, 
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		// Check category name valid
		boolean isValid = categoryRepository.findAll().stream()
				.filter(category -> Constants.DELETED != category.getDeleted())
				.map(Category::getName).toList()
				.contains(name);	
		if(isValid) {
			return ResponseEntity.badRequest().body("Category name valid.");
		}
		
		Path staticPath = Paths.get("E:\\PTIT\\DO_AN_FINAL\\Code_final\\Do_An_FE\\src\\assets\\imgs"); // src/main/resources/static/img
        Path imagePath = Paths.get("");
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path file = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(multipartFile.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(multipartFile.getBytes());
        }
		
		name = URLDecoder.decode(name, "UTF-8");
		Category category = new Category();
		category.setName(name);
		category.setImgSrc(imagePath.resolve(multipartFile.getOriginalFilename()).toString());
		return ResponseEntity.ok(categoryRepository.save(category));
	}
	
	/**
	 * Delete by id
	 * @param category
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Category not found with id " + id));
		
		// Delete category
		category.setDeleted(Constants.DELETED);
		categoryRepository.save(category);
		
		// Delete all product in category
		List<Product> productsInCategory = productRepository.findAllByCategoryIds(id);
		productsInCategory.forEach(product -> {
			product.setDeleted(Constants.DELETED);
		});
		productRepository.saveAll(productsInCategory);
		
		// Delete all product in cart
		productsInCategory.forEach(product -> {
			cartRepository.deleteByIdProduct(product.getId());
		});
		
		return ResponseEntity.noContent().build();
	}
}
