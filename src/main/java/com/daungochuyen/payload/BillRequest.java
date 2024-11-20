package com.daungochuyen.payload;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.daungochuyen.message.Message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Validate bill request
 * @author ADMIN
 *
 */
@Data
@AllArgsConstructor
public class BillRequest {
	@NotNull(message = Message.NOT_NULL)
	private Long userId;
	
	@NotBlank(message = Message.NOT_BLANK)
	private List<ProductRequest> productRequests; 
}
