package com.daungochuyen.mapper;

import com.daungochuyen.dto.ProductDTO;
import com.daungochuyen.entity.Category;
import com.daungochuyen.entity.Product;
import com.daungochuyen.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductMapper {
	
	public static ProductDTO toDTO(Product product, CategoryRepository categoryRepository) {
		Category category = categoryRepository.findById(product.getCategoryId()).get();
		
        ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setPhotos(product.getPhotos());
		productDTO.setPrice(product.getPrice());
		productDTO.setQuantity(product.getQuantity());
		productDTO.setDiscount(product.getDiscount());
		productDTO.setDeleted(product.getDeleted());
		productDTO.setCategory(category);

        return productDTO;
    }
}
