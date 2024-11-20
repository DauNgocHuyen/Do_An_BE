package com.daungochuyen.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.daungochuyen.dto.UserDTO;
import com.daungochuyen.entity.User;
import com.daungochuyen.jwt.JwtAuthenticationFilter;
import com.daungochuyen.jwt.JwtTokenProvider;
import com.daungochuyen.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * User manager controller
 * @author ADMIN
 *
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class UserController {
	private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	/*
	 * Get alls
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	/*
	 * Get user info by token
	 */
	@GetMapping("/user-profile")
	public ResponseEntity<?> getUserByToken(HttpServletRequest request) {
		String token = JwtAuthenticationFilter.getJwtFromRequest(request);
		Long id = jwtTokenProvider.getUserIdFromJWT(token);
		User user = userRepository.findById(id).orElseThrow(
				() -> new UsernameNotFoundException("User id invalid.")
		);
		
		return ResponseEntity.ok(user);
	}
	
	
	/*
	 * Update user profile
	 */
	@PutMapping("/users-profile")
	public ResponseEntity<?> updateUserProfile(@RequestBody UserDTO userDTO, HttpServletRequest request) {
		String token = JwtAuthenticationFilter.getJwtFromRequest(request);
		Long id = jwtTokenProvider.getUserIdFromJWT(token);
		User user = userRepository.findById(id).orElseThrow(
				() -> new UsernameNotFoundException("User id invalid.")
		);
		
		user.setUsername(userDTO.getUsername());
		user.setCity(userDTO.getCity());
		user.setCountry(userDTO.getCountry());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setPhone(userDTO.getPhone());
		
		userRepository.save(user);
		return ResponseEntity.noContent().build();
	}
	
	/*
	 * Update user avatar
	 */
	@PutMapping("/users-profile/avatar")
	public ResponseEntity<?> updateUserAvatar(
			@RequestParam("avatarImg") MultipartFile multipartFile, 
			HttpServletRequest request) throws IOException {
		String token = JwtAuthenticationFilter.getJwtFromRequest(request);
		Long id = jwtTokenProvider.getUserIdFromJWT(token);
		User user = userRepository.findById(id).orElseThrow(
				() -> new UsernameNotFoundException("User id invalid.")
		);
		
		Path staticPath = Paths.get("E:\\PTIT\\DO AN\\Code\\Code\\furniture-store-fe\\Furniture-Store-FE\\src\\assets\\imgs");
        Path imagePath = Paths.get("");
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path file = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(multipartFile.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(multipartFile.getBytes());
        }
        
        user.setAvatarUrl(imagePath.resolve(multipartFile.getOriginalFilename()).toString());
		userRepository.save(user);
		
		return ResponseEntity.noContent().build();
	}
	
	
	
	/*
	 * Get user by id
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/users/{id}")
	public User getUser(@PathVariable("id") Long id) {
		return userRepository.findById(id).orElseThrow(
				() -> new UsernameNotFoundException("User id invalid.")
		);
	}

	
	/*
	 * Update user with id
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@PutMapping("/users/{id}")
	public User updateUser(@PathVariable("id") Long id, @Valid @RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	
	/*
	 * Delete user by id
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable("id") Long id) {
		userRepository.deleteById(id);
	}
}