package com.daungochuyen.payload;

import lombok.Data;

/**
 * Return login
 * @author ADMIN
 *
 */
@Data
public class LoginResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	
	public LoginResponse(String accessToken) {
		this.accessToken = accessToken;
	}
}
