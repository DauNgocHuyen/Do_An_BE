package com.daungochuyen.payload;

import com.daungochuyen.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Return product
 * @author ADMIN
 *
 */
@Data
@AllArgsConstructor
public class ProductResponse {
	private Product product;
	private Long quantitySelected;
}
