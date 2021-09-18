package com.loginapp.web.ui.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorControllerForUI  implements ErrorController{

	
	@GetMapping("/error")
	public String errorCaptured(){
		System.out.println("=======Landed in ERROR Controller==================");
		return "/login";
	}
}
