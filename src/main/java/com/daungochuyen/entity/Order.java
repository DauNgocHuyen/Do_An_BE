package com.daungochuyen.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Order 1 - n Product Order
 * @author ADMIN
 *
 */
@Entity
@Table(name = "order_detail")
@Data
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long userId;
	
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
	
}
