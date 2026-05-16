package com.springsecurity.security01.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.springsecurity.security01.entity.User;
import com.springsecurity.security01.service.UserService;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserService userService; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("auth -2");
		User dbUser = userService.getUserByUserName(username);
		if(dbUser == null) {
			throw new UsernameNotFoundException("User Not Found");
		}
		System.out.println(dbUser.getUserName());
		System.out.println("auth -5");
		return org.springframework.security.core.userdetails.User.
				withUsername(dbUser.getUserName()).password(dbUser.getPassword())
				.roles(dbUser.getRole().toUpperCase().replace("ROLE_", ""))
				.build();
	}

}
