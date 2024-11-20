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
	 */
	void payment(PaymentRequest paymentRequest, String token);
}
