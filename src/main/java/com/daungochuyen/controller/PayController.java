package com.daungochuyen.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daungochuyen.jwt.JwtAuthenticationFilter;
import com.daungochuyen.payload.PaymentRequest;
import com.daungochuyen.service.PayService;

/**
 * Payment manager controller
 * Created by: ADMIN
 * Version: 1.0
 */
@RestController
@RequestMapping("/api/v1/payment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PayController {
	
	@Autowired
	private PayService payService;
	
	
	/*
	 * Clear cart after payment success
	 * Created by: ADMIN
	 * Version: 1.0
	 */
	@PostMapping()
	public ResponseEntity<?> payment(@RequestBody PaymentRequest paymentRequest, HttpServletRequest request) throws Exception {
		String token = JwtAuthenticationFilter.getJwtFromRequest(request);
		payService.payment(paymentRequest, token);
		return ResponseEntity.noContent().build();
	}
	
}
