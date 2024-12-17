package com.daungochuyen.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daungochuyen.entity.Cart;
import com.daungochuyen.entity.Category;
import com.daungochuyen.entity.Product;
import com.daungochuyen.jwt.JwtAuthenticationFilter;
import com.daungochuyen.jwt.JwtTokenProvider;
import com.daungochuyen.payload.CartResponse;
import com.daungochuyen.payload.ProductRequest;
import com.daungochuyen.repository.CartRepository;
import com.daungochuyen.repository.CategoryRepository;
import com.daungochuyen.repository.ProductRepository;
import com.daungochuyen.repository.UserRepository;
import com.daungochuyen.service.CartService;

/**
 * Cart service implements
 * @author ADMIN
 *
 */
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	
	/*
	 * Get all products in cart
	 * Created by: ADMIN
	 * Version: 1.0
	 */
	@Override
	public List<CartResponse> getAllCart(HttpServletRequest request) {
		String token = JwtAuthenticationFilter.getJwtFromRequest(request);
		List<CartResponse> listProducts = new ArrayList<CartResponse>();
		
		// Get list product from cart if token is valid
		Long idUser = jwtTokenProvider.getUserIdFromJWT(token);
		List<Cart> listCarts = cartRepository.findByIdUser(idUser);
		if( listCarts != null ) {
			for(Cart cart: listCarts) {
				Long idCart = cart.getId();
				Long idProduct = cart.getIdProduct();
				Long quantity = cart.getQuantity();
				Product product = productRepository.findById(idProduct).get();
				Category category = categoryRepository.findById(product.getCategoryId()).get();
				CartResponse cartResponse = new CartResponse(idCart, product, quantity, category.getName());
				listProducts.add(cartResponse);
			}							
		}
		
//		handlePayment(modelAndView, listProducts, request);
		
		return listProducts;
	}

	/*
	 * Add product to cart
	 * Created by: ADMIN
	 * Version: 1.0
	 */
	@Override
	public Cart addToCart(ProductRequest productRequest, HttpServletRequest request) {
		// Get user id from token:
		String token = JwtAuthenticationFilter.getJwtFromRequest(request);
		Long idUser = jwtTokenProvider.getUserIdFromJWT(token);
		Long idProduct = productRequest.getIdProduct();
		Long quantitySelected = productRequest.getQuantitySelected();
		
		// Update cart quantity:
		List<Cart> listCarts = cartRepository.findByIdProduct(idProduct);
		if( !listCarts.isEmpty() ) {
			for(Cart cart : listCarts) {
				if( cart.getIdUser() == idUser ) {
					Long quantity = cart.getQuantity() + quantitySelected;
					cart.setQuantity(quantity);
					return cartRepository.save(cart);
				}
			}
		}
		Cart cart = new Cart();
		cart.setIdProduct(idProduct);
		cart.setIdUser(idUser);
		cart.setQuantity(quantitySelected);		
		
		return cartRepository.save(cart);
	}


	/*
	 * Return cart size
	 * Created by: ADMIN
	 * Version: 1.0
	 */
	@Override
	public int countCartSize(HttpServletRequest request) {
		String token = JwtAuthenticationFilter.getJwtFromRequest(request);
		Long idUser = jwtTokenProvider.getUserIdFromJWT(token);
		
		List<Cart> listProductInCarts = cartRepository.findByIdUser(idUser);
		if( listProductInCarts != null ) {
			return listProductInCarts.size();		
		}
		
		return 0;
	}


	/*
	 * Update product quantity
	 * Version: 1.0
	 */
	@Override
	public Cart update(Long id, Long quantityUpdate) {
		Cart cart = cartRepository.findById(id).get();
		Long quantityCurrent = cart.getQuantity();
		cart.setQuantity(quantityCurrent + quantityUpdate);
		cartRepository.save(cart);
		return cart;
	}
	
}
