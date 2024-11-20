package com.daungochuyen.controller.admin;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daungochuyen.entity.Order;
import com.daungochuyen.repository.OrderRepository;

@RestController
@RequestMapping("/api/v1/admin/order")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminOrderController {
	@Autowired
	OrderRepository orderDetailRepository;
	
	/**
	 * Get all order
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<?> getAll(HttpServletRequest request) {
		List<Order> orders = orderDetailRepository.findAll();
		Collections.sort(orders, Comparator.comparing(Order::getOrderDate).reversed());
		return ResponseEntity.ok(orders);
	}
}
