package com.daungochuyen.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.daungochuyen.dto.ProductDTO;
import com.daungochuyen.payload.ProductCriteria;

public interface ProductService {
	
	Page<ProductDTO> getAll(ProductCriteria criteria, Pageable pageable);
	
	List<ProductDTO> getAllProductDiscount();
	
	List<ProductDTO> filterByCategory(Long id);
	
	List<ProductDTO> searchByName(String name);
	
	void updateProduct(Long id, ProductDTO productDTO);

	void deleteProduct(Long id);
	
	List<ProductDTO> filterDiscountByCategory(Long id);
	
	List<ProductDTO> searchDiscountByName(String name);
	
	List<ProductDTO> findNewProducts();
}
