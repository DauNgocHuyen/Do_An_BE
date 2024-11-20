package com.daungochuyen.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.daungochuyen.entity.Cart;
import com.daungochuyen.payload.CartResponse;
import com.daungochuyen.payload.ProductRequest;

/**
 * Cart service
 * @author ADMIN
 *
 */
public interface CartService {
	/**
	 * Get all products from cart
	 * @param modelAndView
	 * @param request
	 * @return
	 */
	List<CartResponse> getAllCart(HttpServletRequest request);
	
	/**
	 * Add product to cart
	 * @param productRequest
	 * @param request
	 * @return
	 */
	Cart addToCart(ProductRequest productRequest, HttpServletRequest request);

	/**
	 * Count cart size
	 * @param request
	 */
	int countCartSize(HttpServletRequest request);
	
	/**
	 * Update product quantity from cart
	 * @param id
	 * @param quantityUpdate
	 * @return
	 */
	Cart update(Long id, Long quantityUpdate);
}
