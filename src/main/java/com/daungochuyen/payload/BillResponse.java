package com.daungochuyen.payload;

import java.util.Date;
import java.util.List;

import com.daungochuyen.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Return bill
 * @author ADMIN
 *
 */
@Data
@AllArgsConstructor
public class BillResponse {
	private Long billId;
	private User user;
	private List<ProductResponse> products;
	private Date orderDate;
	private Integer status;
	private Double total;
}
