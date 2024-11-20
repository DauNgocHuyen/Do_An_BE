package com.daungochuyen.service;

import java.util.Map;

/**
 * VNPay service
 * @author ADMIN
 *
 */
public interface VNPayService {
	
	/**
	 * Return payment data
	 * @param amount
	 * @param bankCode
	 * @param bankTranNo
	 * @param cardType
	 * @param orderInfo
	 * @param payDate
	 * @param responseCode
	 * @param tmnCode
	 * @param transactionNo
	 * @param transactionStatus
	 * @param txnRef
	 * @param secureHash
	 * @return
	 */
	Map<String, String> vnpayResponse(String amount, String bankCode, String bankTranNo, String cardType, String orderInfo,
			String payDate, String responseCode, String tmnCode, String transactionNo, String transactionStatus,
			String txnRef, String secureHash);
}
