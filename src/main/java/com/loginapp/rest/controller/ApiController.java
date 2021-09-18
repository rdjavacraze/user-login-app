package com.loginapp.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

	@GetMapping("/welcome")
	public String welcomMsg(){
		return "Welcome to Spring security learning";
	}
}
