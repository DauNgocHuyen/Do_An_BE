package com.daungochuyen.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.daungochuyen.entity.User;

/**
 * Auth service
 * @author ADMIN
 *
 */
public interface AuthService {
	/**
	 * Check login
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 */
	String handleLogin(String username, String password, HttpServletRequest request);
	
	/**
	 * Register new account
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception 
	 */
	User handleRegister(String username, String password, String firstName, String lastName) throws Exception;
	
	/**
	 * Logout service
	 * @param request
	 * @param response
	 */
	void handleLogout(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Validate token
	 * @param page
	 * @param session
	 * @return
	 */
	ModelAndView validateToken(String page, HttpSession session);
	
}
