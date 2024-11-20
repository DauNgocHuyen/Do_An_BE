package com.daungochuyen.controller.admin;

import java.io.IOException;
import java.net.URLDecoder;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.daungochuyen.entity.Product;
import com.daungochuyen.repository.ProductRepository;
import com.daungochuyen.service.admin.AdminProductService;

/**
 * Product manager controller
 */
@RestController
@RequestMapping("/admin/api/v1/products")
public class AdminProductController {
	
	@Autowired
	AdminProductService adminProductService;
	
	@Autowired
	ProductRepository productRepository;
	
	/*
	 * Add new product
	 * Version: 1.0
	 */
	@PostMapping()
	public ResponseEntity<?> addImage(
			@RequestParam("name") String name, 
			@RequestParam("price") Long price, 
			@RequestParam("quantity") Long quantity,
			@RequestParam("categoryId") Long categoryId,
			@RequestParam("image") MultipartFile multipartFile) throws IOException  
	{
		name = URLDecoder.decode(name, "UTF-8");
		adminProductService.addNewProduct(name, price, quantity, categoryId, multipartFile);
        return ResponseEntity.noContent().build();
	}

	
	/*
	 * Update product with id
	 */
	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable("id") Long id, @Valid @RequestBody Product product) {
		return productRepository.save(product);
	}

	
	/*
	 * Delete product by id
	 */
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable("id") Long id) {
		adminProductService.deleteProduct(id);
	}
}
