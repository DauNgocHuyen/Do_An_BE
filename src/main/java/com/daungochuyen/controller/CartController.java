package com.daungochuyen.controller;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.daungochuyen.entity.Cart;
import com.daungochuyen.jwt.JwtAuthenticationFilter;
import com.daungochuyen.payload.CartResponse;
import com.daungochuyen.payload.PaymentRequest;
import com.daungochuyen.payload.ProductRequest;
import com.daungochuyen.repository.CartRepository;
import com.daungochuyen.service.CartService;
import com.daungochuyen.vnpay.Config;

/**
 * Cart manager controller
 */
@RestController
@RequestMapping("/api/v1/cart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartController {
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	CartService cartService;
	
	/*
	 * Add product to cart
	 */
	@PostMapping()
	public ResponseEntity<?> addToCart(@Valid @RequestBody ProductRequest productRequest, HttpServletRequest request)  {
		Cart response = cartService.addToCart(productRequest, request);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Show cart details
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<?> getAll(HttpServletRequest request) {
		List<CartResponse> response = cartService.getAllCart(request);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Get cart size
	 * @param request
	 * @return
	 */
	@GetMapping("/size")
	public ResponseEntity<?> getCartSize(HttpServletRequest request) {
		int size = cartService.countCartSize(request);
		return ResponseEntity.ok(size);
	}
	
	/*
	 * Update quantity
	 */
	@PutMapping("/{id}")
	public Cart update(@PathVariable("id") Long id, @RequestBody Long quantityUpdate) {
		return cartService.update(id, quantityUpdate);
	}
	
	
	/*
	 * Delete product from cart
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		cartRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Save paymentRequest
	 */
	@PostMapping("/payment/request")
	public ResponseEntity<?> savePaymentRequest(@RequestBody PaymentRequest paymentRequest, HttpServletRequest request) {
		String token = JwtAuthenticationFilter.getJwtFromRequest(request);
		Config.token = token;
		Config.paymentRequest = Objects.requireNonNullElse(paymentRequest, new PaymentRequest());
		return ResponseEntity.noContent().build();
	}
	
	/*
	 * Show payment online page
	 */
	@GetMapping("/payment/{amount}")
	public ModelAndView payment(
			@PathVariable("amount") Long amount, 
			HttpSession session
	) {
		System.out.println("Payment online page");
		ModelAndView modelAndView = new ModelAndView("vnpay");
		modelAndView.addObject("amount", amount);
		return modelAndView;
	}
	
}
