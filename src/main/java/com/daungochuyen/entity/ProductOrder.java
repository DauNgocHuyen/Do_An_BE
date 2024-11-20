package com.daungochuyen.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * Product Order n - 1 Order details
 * @author ADMIN
 *
 */
@Entity
@Data
public class ProductOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long orderDetailId;
	
	private Long productId;
	
	private Long quantitySelected;
}
