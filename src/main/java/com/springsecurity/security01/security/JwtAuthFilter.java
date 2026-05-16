package com.springsecurity.security01.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

    
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String path = request.getServletPath();

		if (path.equals("/user/login") || path.equals("/user/register")) {
		    filterChain.doFilter(request, response);
		    return;
		}

		String username = null;
		String token = request.getHeader("Authorization");
		
		System.out.println(token);
		
		if(token!=null && !token.isEmpty()){
			token = token.substring(7);
			if(!token.isEmpty()){
				
				 if (!jwtUtil.validateToken(token)) {
			            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Token expired or invalid — reject without setting auth
			            response.getWriter().write("Token expired or invalid");
			        	//filterChain.doFilter(request, response);
			            return;
				 }
			        }
		}
						
	 			System.out.println(token);
				username = jwtUtil.extractUsername(token);
				String role = jwtUtil.extractRole(token);
			
			System.out.println("user is "+username);
			System.out.println("user is "+role);
			if(username != null && role!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
				  // ✅ Build authority directly from the JWT claim — no DB call needed
	            List<SimpleGrantedAuthority> authorities =
	                    List.of(new SimpleGrantedAuthority(role));
	        
				UserDetails user = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
						UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorities);// user.getAuthorities()
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		filterChain.doFilter(request, response);
		// method of  OncePerRequestFilter 
		//
	}
}
