package com.daungochuyen.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.daungochuyen.message.Message;

import lombok.Data;

/**
 * Cart entity
 * @author ADMIN
 *
 */
@Entity
@Table(name = "cart")
@Data
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Product id */
	@NotNull(message = Message.NOT_NULL)
	private Long idProduct;
	
	/** User id */
	@NotNull(message = Message.NOT_NULL)
	private Long idUser;
	
	/** Quantity selected */
	@NotBlank(message = Message.NOT_BLANK)
	private Long quantity;
	
}
