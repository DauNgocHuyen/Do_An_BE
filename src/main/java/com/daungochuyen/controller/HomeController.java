package com.daungochuyen.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.daungochuyen.repository.ProductRepository;
import com.daungochuyen.service.AuthService;
import com.daungochuyen.service.CartService;
import com.daungochuyen.service.VNPayService;
import com.daungochuyen.service.admin.AdminProductService;

/**
 * Page manager controller
 * Created by: NPTAN
 * Version: 1.0
 */
@RestController
@RequestMapping("/api")
public class HomeController {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private VNPayService vnPayService;
	
	@Autowired
	private AdminProductService adminProductService;
	
	@Autowired 
	private AuthService authService;
	
	
	/*
	 * View home (get list product)
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/")
	public ModelAndView indexPage(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("products", productRepository.findAll());
		cartService.countCartSize(request);
		return modelAndView;
	}
	
	
	/*
	 * View login 
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("auth/login")
	public ModelAndView loginPage(HttpSession session){
		return authService.validateToken("login", session);
	}
	
	
	/*
	 * View register 
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("auth/register")
	public ModelAndView registerPage(HttpSession session) {
		return authService.validateToken("register", session);
	}
	
	
	/*
	 * View product manager
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/admin-product")
	public ModelAndView productPage() {
		return adminProductService.productPage();
	}
	
	
	/*
	 * View add new product
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/admin/add-product")
	public ModelAndView addProductPage() {
		return new ModelAndView("admin/add-product");
	}
	
	
	/*
	 * View import list product
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/admin/add-list")
	public ModelAndView addListProduct() {
		return new ModelAndView("admin/add-list-product");
	}
	
	
	/*
	 * View product detail
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/detail")
	public ModelAndView detailPage(@RequestParam("id") Long id, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("detail");
		modelAndView.addObject("product", productRepository.findById(id));
		cartService.countCartSize(request);
		return modelAndView;
	}
	
	
	/*
	 * View list product in cart
	 * Created by: NPTAN
	 * Version: 1.0
	 */
//	@GetMapping("/list-cart")
//	public ModelAndView cartPage(HttpServletRequest request) {
//		ModelAndView modelAndView = new ModelAndView("cart");
//		cartService.getAllCart(modelAndView, request);
//		return modelAndView;
//	}
	
	
	/*
	 * View add new discount
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/admin/add-discount")
	public ModelAndView createDiscount() {
		return new ModelAndView("admin/add-discount");
	}
	
	
	/*
	 * View online payment (VNPay)
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/payment")
	public ModelAndView payment() {
		return new ModelAndView("vnpay");
	}
	
	
	/*
	 * View return online payment
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/vnpay_return")
	public ModelAndView returnPage(
			@RequestParam("vnp_Amount") String amount,
			@RequestParam("vnp_BankCode") String bankCode,
			@RequestParam("vnp_BankTranNo") String bankTranNo,
			@RequestParam("vnp_CardType") String cardType,
			@RequestParam("vnp_OrderInfo") String orderInfo,
			@RequestParam("vnp_PayDate") String payDate,
			@RequestParam("vnp_ResponseCode") String responseCode,
			@RequestParam("vnp_TmnCode") String tmnCode,
			@RequestParam("vnp_TransactionNo") String transactionNo,
			@RequestParam("vnp_TransactionStatus") String transactionStatus,
			@RequestParam("vnp_TxnRef") String txnRef,
			@RequestParam("vnp_SecureHash") String secureHash
			
	) {
		ModelAndView modelAndView = new ModelAndView("vnpay_return");
		
		long total = Long.parseLong(amount) / 100;
		modelAndView.addObject("data", vnPayService.vnpayResponse(String.valueOf(total), bankCode, bankTranNo, 
				cardType, orderInfo, payDate, responseCode, tmnCode, transactionNo, transactionStatus, txnRef, secureHash));
		return modelAndView;
	}
}
