package com.springsecurity.security01.service;

import com.springsecurity.security01.entity.User;


public interface UserService {
	
	User registerUser(User u);
	
	User getUserByUserName(String userName);
}
