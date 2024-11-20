package com.daungochuyen.service;

import com.daungochuyen.dto.OrderDetailDTO;

public interface OrderService {
	/**
	 * Get order details by id
	 * @param id
	 * @return
	 */
	OrderDetailDTO getOrderDetails(Long id);
}
