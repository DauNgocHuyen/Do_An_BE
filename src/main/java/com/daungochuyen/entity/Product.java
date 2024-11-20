package com.daungochuyen.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.daungochuyen.message.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product entity
 * @author ADMIN
 *
 */
@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Product(String name, Long price, Long quantity, String photos) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.photos = photos;
	}
	
	/** Product name */
	@NotBlank(message = Message.NOT_BLANK)
	private String name;
	
	/** Product price */
	@NotNull(message = Message.NOT_NULL)
	private Long price;
	
	/** Product quantity */
	@NotNull(message = Message.NOT_NULL)
	private Long quantity;
	
	/** Product photos */
	private String photos;
	
	private Long categoryId;
	
	/** Percent ( % ) */
	private int discount;
	
	private int deleted;
}
