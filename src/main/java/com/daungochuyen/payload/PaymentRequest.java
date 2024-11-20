package com.daungochuyen.payload;

import java.util.List;

import com.daungochuyen.dto.ProductItem;

import lombok.Data;

@Data
public class PaymentRequest {
	
	private List<ProductItem> productInfo;
	
	private String fullName;
	
	private String email;
	
	private String phone;
	
	private String company;
	
	private String zip;
	
	private String city;
	
	private String district;

	private String detailAddress;
	
	private Long total;

}
