package com.springsecurity.security01.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	String key = "mysecretkeymysecretkeymysecretkeymysecretkeymysecretkeymysecretkey";
	
	public String genrateToken(String userName,String role) {
		System.out.println("hello"+userName);
		Map<String, String> map = new HashMap<>();
		map.put(userName, role);
		String token = Jwts.builder()
				.signWith(getSigningKey(),SignatureAlgorithm.HS256)
				.setSubject(userName) 
				.claim("role", role)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
				.compact();
		
		System.out.println(token);
		return token;
	}
	

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(key.getBytes());
	}

	public String extractUsername(String token) {
		String subject = null;
		try {

			subject = extractAllClaims(token).getSubject();
			System.out.println(subject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subject;
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token).getBody();
	}

	public boolean validateToken(String token) {
		try {
			Date expiration = extractAllClaims(token).getExpiration();
			System.out.println(expiration.getTime() > System.currentTimeMillis());
			
			return expiration.getTime() > System.currentTimeMillis();
			// return extractAllClaims(token).getExpiration().before(new Date());
		} catch (ExpiredJwtException e) {
			//e.printStackTrace();
			return false;  // token is expired — treat as invalid
		} catch (JwtException e) {
			return false;  // malformed, tampered, etc.
		}
	}
	
	 public String extractRole(String token) {
	        try {
	            return (String) extractAllClaims(token).get("role");
	        } catch (Exception e) {
	            return null;
	        }
	    }
}
