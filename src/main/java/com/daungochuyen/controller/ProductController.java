package com.daungochuyen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daungochuyen.abc.Rating;
import com.daungochuyen.dto.ProductDTO;
import com.daungochuyen.entity.Category;
import com.daungochuyen.entity.Product;
import com.daungochuyen.payload.ProductCriteria;
import com.daungochuyen.repository.CartRepository;
import com.daungochuyen.repository.CategoryRepository;
import com.daungochuyen.repository.ProductRepository;
import com.daungochuyen.service.ProductService;

import lombok.RequiredArgsConstructor;

/**
 * Product manager controller
 * Created by: ADMIN
 * Version: 1.0
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private List<Rating> ratings;
	
	/*
	 * Get all
	 * Created by: ADMIN
	 * Version: 1.0
	 */
	@GetMapping()
	public ResponseEntity<Page<ProductDTO>> getAllProducts(
		ProductCriteria criteria,
		@RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "12") int size
	) {
		Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> result = productService.getAll(criteria, pageable);
        ratings.get(0).getRating();

		return ResponseEntity.ok(result);
	}
	
	/**
	 * Get all product discount
	 * @return
	 */
	@GetMapping("/discounts")
	public ResponseEntity<?> getAllProductDiscounts() {
		return ResponseEntity.ok(productService.getAllProductDiscount());
	}
	
	/**
	 * Filter product discount by category
	 * @return
	 */
	@GetMapping("/category/discounts/{id}")
	public ResponseEntity<?> filterDiscountByCategory(@PathVariable("id") Long id) {
		return ResponseEntity.ok(productService.filterDiscountByCategory(id));
	}
	
	
	/*
	 * Get product by id
	 * Created by: ADMIN
	 * Version: 1.0
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
		Product product = productRepository.findById(id).orElseThrow(
				() -> new UsernameNotFoundException("Product id invalid.")
		);
		
		Category category = categoryRepository.findById(product.getCategoryId()).get();		
		ProductDTO productDTO = new ProductDTO(product, category);
		return ResponseEntity.ok(productDTO);
	}
	
	/**
	 * Get product by category
	 * @param id category id
	 * @return
	 */
	@GetMapping("/category/{id}")
	public ResponseEntity<?> getProductByCategoryId(@PathVariable("id") Long id) {
		return ResponseEntity.ok(productService.filterByCategory(id));
	}
	
	/**
	 * Search by name (like)
	 * @param name
	 * @return
	 */
	@GetMapping("/search")
	public ResponseEntity<?> searchProductByName(@RequestParam("name") String name) {
		return ResponseEntity.ok(productService.searchByName(name));
	}
	
	/**
	 * Search by name (like)
	 * @param name
	 * @return
	 */
	@GetMapping("/discounts/search")
	public ResponseEntity<?> searchProductDiscountByName(@RequestParam("name") String name) {
		return ResponseEntity.ok(productService.searchDiscountByName(name));
	}
	
	/**
	 * Update product info
	 * @param id
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDTO productDTO) {
		productService.updateProduct(id, productDTO);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Delete product
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
		// Delete product
		productService.deleteProduct(id);
		
		// Delete product from cart
		cartRepository.deleteByIdProduct(id);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Get new products
	 */
	@GetMapping("/new")
	public ResponseEntity<?> getNewProducts() {
		return ResponseEntity.ok(productService.findNewProducts());
	}
	
}
