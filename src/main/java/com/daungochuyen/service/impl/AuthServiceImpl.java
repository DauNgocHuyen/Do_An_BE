package com.daungochuyen.service.impl;

import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.daungochuyen.custom.CustomUserDetails;
import com.daungochuyen.entity.User;
import com.daungochuyen.jwt.JwtTokenProvider;
import com.daungochuyen.payload.LoginRequest;
import com.daungochuyen.repository.UserRepository;
import com.daungochuyen.service.AuthService;

/**
 * Auth service implements
 * @author ADMIN
 *
 */
@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	/*
	 * Check login
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public String handleLogin(String username, String password, HttpServletRequest request) {
		String jwt = null;
		try {
			LoginRequest loginRequest = new LoginRequest(username, password);
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getUsername(), 
							loginRequest.getPassword()
					)
			);
			// Set authentication into Security Context
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			// Generate token
			jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
			HttpSession session = request.getSession();
			session.setAttribute("token", jwt);
			Long userId = tokenProvider.getUserIdFromJWT(jwt);
			User user = userRepository.getById(userId);
			session.setAttribute("role", user.getRole());
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return jwt;
	}

	
	/*
	 * Register new account
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public User handleRegister(String username, String password, String firstName, String lastName) throws Exception {
		// Check email existed
		if(userRepository.checkEmailExist(username) > 0) {
			throw new Exception("Email " + username + " existed!");
		}
		
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole("USER");
		userRepository.save(user);
		return user;
	}

	
	/*
	 * Logout service
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public void handleLogout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute("token");
		session.removeAttribute("username");
		session.removeAttribute("role");
		// Reset cookies:
		if(Objects.nonNull(request.getCookies())) {
			for (Cookie cookie : request.getCookies()) {
			    cookie.setValue("");
			    cookie.setMaxAge(0);
			    cookie.setPath("/");
			    response.addCookie(cookie);
			}
		}
		
	}


	/*
	 * Validate token
	 */
	@Override
	public ModelAndView validateToken(String page, HttpSession session) {
		String token = (String) session.getAttribute("token");
		if(token != null) {
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView(page);
	}

}
