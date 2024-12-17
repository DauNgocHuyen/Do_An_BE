package com.daungochuyen.service.impl;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daungochuyen.dto.ProductItem;
import com.daungochuyen.entity.Cart;
import com.daungochuyen.entity.Order;
import com.daungochuyen.entity.Product;
import com.daungochuyen.entity.ProductOrder;
import com.daungochuyen.jwt.JwtTokenProvider;
import com.daungochuyen.payload.PaymentRequest;
import com.daungochuyen.repository.CartRepository;
import com.daungochuyen.repository.OrderRepository;
import com.daungochuyen.repository.ProductOrderRepository;
import com.daungochuyen.repository.ProductRepository;
import com.daungochuyen.service.PayService;
import com.daungochuyen.utils.GenerateOrderCodeUtils;

/**
 * Payment service implements
 * @author ADMIN
 *
 */
@Service
public class PayServiceImpl implements PayService{

	@Autowired
	CartRepository cartRepository;
	
	@Autowired 
	ProductRepository productRepository;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	OrderRepository orderDetailRepository;
	
	@Autowired
	ProductOrderRepository productOrderRepository;
	
//	private Date generateRandomOrderDate() {
//        int month = ThreadLocalRandom.current().nextInt(1, 2);
//        
//        int day = ThreadLocalRandom.current().nextInt(1, Month.of(month).maxLength() + 1);
//        
//        return Date.from(LocalDate.of(2024, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
//    }
	
	/*
	 * Payment bill -> Clear cart
	 * Created by: ADMIN
	 * Version: 1.0
	 */
	@Override
	public void payment(PaymentRequest paymentRequest, String token) {
		Long userId = jwtTokenProvider.getUserIdFromJWT(token);
		
		// Create order details
		Order orderDetail = new Order();
		orderDetail.setUserId(userId);
		orderDetail.setCity(paymentRequest.getCity());
		orderDetail.setCompany(paymentRequest.getCompany());
		orderDetail.setDetailAddress(paymentRequest.getDetailAddress());
		orderDetail.setDistrict(paymentRequest.getDistrict());
		orderDetail.setEmail(paymentRequest.getEmail());
		orderDetail.setFullName(paymentRequest.getFullName());
		orderDetail.setPhone(paymentRequest.getPhone());
		orderDetail.setTotal(paymentRequest.getTotal());
		orderDetail.setZip(paymentRequest.getZip());
		orderDetail.setStatus(0);
		orderDetail.setCode(GenerateOrderCodeUtils.generateOrderCode());
		
//		orderDetail.setOrderDate(generateRandomOrderDate());
		
		orderDetail = orderDetailRepository.save(orderDetail);
		
		// Create product order (product & quantity)
		List<ProductItem> productPaymentDTOs = paymentRequest.getProductInfo();
		for(ProductItem product : productPaymentDTOs) {
			ProductOrder productOrder = new ProductOrder();
			productOrder.setOrderDetailId(orderDetail.getId());
			productOrder.setProductId(product.getProductId());
			productOrder.setQuantitySelected(product.getQuanitySelected());
			
			productOrderRepository.save(productOrder);
			
			// Update product inventory
			Product productEntity = productRepository.findById(product.getProductId()).get();
			Long oldQuantity = productEntity.getQuantity();
			productEntity.setQuantity(oldQuantity - product.getQuanitySelected());
			productRepository.save(productEntity);
			
		}
		
		// Clear cart
		List<Cart> userCarts = cartRepository.findByIdUser(userId);
		cartRepository.deleteAllById(userCarts.stream().map(Cart::getId).toList());
		
	}

}
