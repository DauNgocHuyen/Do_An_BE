package com.daungochuyen.service;

import com.daungochuyen.payload.PaymentRequest;

/**
 * Payment service
 * @author ADMIN
 *
 */
public interface PayService {
	/**
	 * Payment bill
	 * @param request
	 * @throws Exception 
	 */
	void payment(PaymentRequest paymentRequest, String token) throws Exception;
}
