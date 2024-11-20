package com.daungochuyen.payload;

import javax.validation.constraints.NotNull;

import com.daungochuyen.message.Message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Validate login request
 * @author ADMIN
 *
 */
@Data
@AllArgsConstructor
public class LoginRequest {
	@NotNull(message = Message.NOT_NULL)
	private String username;
	
	@NotNull(message = Message.NOT_NULL)
	private String password;
	
}
