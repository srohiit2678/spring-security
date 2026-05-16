package com.springsecurity.security01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springsecurity.security01.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

		User findByUserName(String userName);
}
