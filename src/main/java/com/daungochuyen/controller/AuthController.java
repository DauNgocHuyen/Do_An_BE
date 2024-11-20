package com.daungochuyen.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daungochuyen.dto.LoginDTO;
import com.daungochuyen.entity.User;
import com.daungochuyen.jwt.JwtTokenProvider;
import com.daungochuyen.repository.UserRepository;
import com.daungochuyen.service.AuthService;

import lombok.RequiredArgsConstructor;

/**
 * Auth controller
 */
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserRepository userRepository;
	
	
	/*
	 * Check login
	 */
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(
		@RequestParam("username") String username, 
		@RequestParam("password") String password,
		HttpServletRequest request
	) {
		String token = authService.handleLogin(username, password, request);
		Long userId = tokenProvider.getUserIdFromJWT(token);
		User user = userRepository.getById(userId);
		
		LoginDTO response = new LoginDTO();
		response.setToken(token);
		response.setRole(user.getRole());
		
		if(Objects.isNull(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Message", "Login fail!").build();
		}
		return ResponseEntity.ok(response);
	}
	
	
	/*
	 * Register new account
	 */
	@PostMapping("/register")
	public ResponseEntity<?> createUser(
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("username") String username, 
			@RequestParam("password") String password) throws Exception {
		return ResponseEntity.ok(authService.handleRegister(username, password, firstName, lastName));
	}
	
	
	/*
	 * Logout
	 */
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
		authService.handleLogout(request, response);
		return ResponseEntity.noContent().build();
	}
	
	
}
