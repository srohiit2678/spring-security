package com.springsecurity.security01.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springsecurity.security01.entity.User;
import com.springsecurity.security01.repository.UserRepository;
import com.springsecurity.security01.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User registerUser(User user) {
		
		user = userRepository.save(user);
		
		return user;
	}

	@Override
	public User getUserByUserName(String userName) {
		System.out.println(userName);
		User user = userRepository.findByUserName(userName);
		
		System.out.println("user is: "+user);
		return user;
	}
	
}
