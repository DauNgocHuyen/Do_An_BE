package com.daungochuyen.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderDetailDTO {
	private Long id;
	
	private String code;
	
	private String fullName;
	
	private String email;
	
	private String phone;
	
	private String company;
	
	private String zip;
	
	private String city;
	
	private String district;

	private String detailAddress;
	
	private Long total;
	
	private int status;
	
	private Date orderDate = new Date();
	
	private List<ProductDTO> products;
}
