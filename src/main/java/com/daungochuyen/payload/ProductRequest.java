package com.daungochuyen.payload;

import javax.validation.constraints.NotNull;

import com.daungochuyen.message.Message;

import lombok.Data;

/**
 * Validate product request
 * @author ADMIN
 *
 */
@Data
public class ProductRequest {
	@NotNull(message = Message.NOT_NULL)
	private Long idProduct;
	
	@NotNull(message = Message.NOT_NULL)
	private Long quantitySelected;
}
