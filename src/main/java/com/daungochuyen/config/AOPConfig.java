package com.daungochuyen.config;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * AOP config
 * @author ADMIN
 *
 */
@Aspect
@Configuration
public class AOPConfig {
	
	@Autowired
	HttpSession session;
	
	@Before("execution(* com.daungochuyen.controller.*.*(..))")
	public void handleUserLogin(JoinPoint joinPoint) throws Exception {
		String token = session.getAttribute("token") == null ? null : (String) session.getAttribute("token");
//		if(token == null) {
//			throw new Exception("You are not logged in");
//		}
		System.out.println(token);
	}
	
}
