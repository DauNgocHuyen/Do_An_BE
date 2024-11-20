package com.daungochuyen.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.daungochuyen.custom.CustomUserDetails;
import com.daungochuyen.entity.User;
import com.daungochuyen.repository.UserRepository;

/**
 * User service
 * @author ADMIN
 *
 */
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Check user valid
	 * @param username
	 * @return
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("Username invalid")
		);
		return new CustomUserDetails(user);
	}
	
	/**
	 * Get user by id
	 * @param id
	 * @return
	 */
	@Transactional
	public UserDetails loadUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(
				() -> new UsernameNotFoundException("User not found with id: " + id)
		);
		
		return new CustomUserDetails(user);
	}

}
