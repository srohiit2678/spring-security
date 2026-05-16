package com.springsecurity.security01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/page") 
public class PageController {

	@GetMapping("/deshboard")
	public String deshboardAdmin() {
		return "admin deshboard";
	}
	
	@GetMapping("/deshboard-user")
	public String deshboardUser() {
		return "deshboard";
	}
	
}
