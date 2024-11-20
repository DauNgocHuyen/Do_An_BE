package com.daungochuyen.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daungochuyen.entity.Order;
import com.daungochuyen.entity.Product;
import com.daungochuyen.entity.ProductOrder;
import com.daungochuyen.jwt.JwtAuthenticationFilter;
import com.daungochuyen.jwt.JwtTokenProvider;
import com.daungochuyen.repository.OrderRepository;
import com.daungochuyen.repository.ProductOrderRepository;
import com.daungochuyen.repository.ProductRepository;
import com.daungochuyen.service.OrderService;

@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {
	
	@Autowired
	OrderRepository orderDetailRepository;
	
	@Autowired
	ProductOrderRepository productOrderRepository;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	/**
	 * Get all order
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<?> getAll(HttpServletRequest request) {
		String token = JwtAuthenticationFilter.getJwtFromRequest(request);
		Long userId = jwtTokenProvider.getUserIdFromJWT(token);
		List<Order> orders = orderDetailRepository.findAllByUserIds(userId);
		Collections.sort(orders, Comparator.comparing(Order::getOrderDate).reversed());
		return ResponseEntity.ok(orders);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getOrderDetails(@PathVariable("id") Long id) {
		return ResponseEntity.ok(orderService.getOrderDetails(id));
	}
	
	/**
	 * Delivered order
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<?> deliveredOrder(@PathVariable("id") Long id) {
		Order order = orderDetailRepository.findById(id).get();
		order.setStatus(1);
		return ResponseEntity.ok(orderDetailRepository.save(order));
	}
	
	/**
	 * Cancel order
	 * @return
	 */
	@PutMapping("/cancel/{id}")
	public ResponseEntity<?> cancelOrder(@PathVariable("id") Long id) {
		Order order = orderDetailRepository.findById(id).get();
		order.setStatus(2);
		
		// Backup product quantity
		List<ProductOrder> productOrders = productOrderRepository.findAllByOrderId(id);
		productOrders.forEach(productOrder -> {
			Product product = productRepository.findById(productOrder.getProductId())
					.orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productOrder.getProductId()));
			
			Long oldQuantity = product.getQuantity();
			product.setQuantity(oldQuantity + productOrder.getQuantitySelected());
			productRepository.save(product);
		});
		
		return ResponseEntity.ok(orderDetailRepository.save(order));
	}
	
	/**
	 * Delete order by id
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		// Delete from order
		orderDetailRepository.deleteById(id);
		
		// Delete product item
		List<ProductOrder> productOrders = productOrderRepository.findAllByOrderId(id);
		productOrders.forEach(item -> {
			productOrderRepository.deleteById(item.getId());
		});
		
		return ResponseEntity.noContent().build();
	}

}
