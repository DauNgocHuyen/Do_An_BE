package com.daungochuyen.payload;

import com.daungochuyen.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Return cart
 * @author ADMIN
 *
 */
@Data
@AllArgsConstructor
public class CartResponse {
	
	private Long id;
	
	private Product product;
	
	private Long quantity;
	
	private String categoryName;
	
}
