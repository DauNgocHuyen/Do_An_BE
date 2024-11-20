package com.daungochuyen.payload;

import lombok.Data;

@Data
public class ProductCriteria {

	private String search;
	
	private Long categoryId;
	
	private Integer isDiscount;
}
