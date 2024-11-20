package com.daungochuyen.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.daungochuyen.custom.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

/**
 * Jwt token provider
 * @author ADMIN
 *
 */
@Component
@Slf4j
public class JwtTokenProvider {

	// JWT SECRET
	private final String JWT_SECRET = "daungochuyen";

	// Validity time of each string jwt
	private final long JWT_EXPIRATION = 604800000L;

	// Create jwt token from user information
	public String generateToken(CustomUserDetails userDetails) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
		return Jwts.builder()
					.setSubject(Long.toString(userDetails.getUser().getId()))
					.setIssuedAt(now)
					.setExpiration(expiryDate)
					.signWith(SignatureAlgorithm.HS512, JWT_SECRET)
					.compact();
	}
	
	// Get user info from jwt
	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser()
							.setSigningKey(JWT_SECRET)
							.parseClaimsJws(token)
							.getBody();
		
		return Long.parseLong(claims.getSubject());
	}
	
	// Validate token
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
			return true;
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty.");
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT token.");
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token.");
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT token.");
		}
		return false;
	}

}
