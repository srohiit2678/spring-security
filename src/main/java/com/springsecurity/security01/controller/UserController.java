package com.springsecurity.security01.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.security01.entity.User;
import com.springsecurity.security01.security.JwtUtil;
import com.springsecurity.security01.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtTokan;
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestParam("username") String userName, @RequestParam String password) {
		try {
			System.out.println("finding user and check name pass");
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
			System.out.println("authentication done");
			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(Map.of("faild", "Login again"));
		}
		 
		System.out.println("lets generate token");
		 UserDetails user = userDetailsService.loadUserByUsername(userName);

		String role = user.getAuthorities().iterator().next().getAuthority(); // e.g. "ROLE_ADMIN"
	    System.out.println("genrate token for: "+user.getUsername());
		String token = jwtTokan.genrateToken(user.getUsername(), role);
		System.out.println(token);
		System.out.println(role);
		return  ResponseEntity.ok(Map.of("token", token));
	}
	
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
				System.out.println(user);
		return ResponseEntity.ok(userService.registerUser(user));
	}
}
