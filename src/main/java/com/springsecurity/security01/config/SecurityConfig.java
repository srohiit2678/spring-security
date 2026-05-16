package com.springsecurity.security01.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springsecurity.security01.security.JwtAuthFilter;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtAuthFilter jwtAuthFilter;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http)throws Exception
	{		
		System.out.println("hello");
		http.csrf(AbstractHttpConfigurer::disable).
			authorizeHttpRequests(authorizationManagerRequestmatcherRegistry ->authorizationManagerRequestmatcherRegistry
					  .requestMatchers("/user/login", "/user/register").permitAll()
					  .requestMatchers("/page/deshboard").hasRole("ADMIN")
					  .requestMatchers("/page/**").hasAnyRole("USER","ADMIN")
					  .anyRequest().authenticated()).
					   addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
			
		return http.build();
	}
	
	@Bean
	AuthenticationManager getAuthenticationManager(AuthenticationConfiguration configuration)throws Exception {
		System.out.println("auth -7");
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
}
