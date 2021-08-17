package com.loginapp.rest.security.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loginapp.security.jwt.util.JWTAuthorizationUtil;
import com.loginapp.security.user.dataobjects.AuthRequest;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

	@Autowired
	private JWTAuthorizationUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager manager;
	@PostMapping(value="/generateToken", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String generateToken(@RequestBody AuthRequest request) throws Exception {

		try {
			manager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),

					request.getPassword()

			));

		} catch (Exception e) {
			throw new Exception("Invalid credentials");
		}
		ObjectMapper objMapper=new ObjectMapper();
		
		Map<String,Object> responseMap=new HashMap<>();
		responseMap.put("accessToken", jwtUtil.generateToken(request.getUsername()));
		responseMap.put("tokenType", "Bearer");
		return objMapper.convertValue(responseMap, String.class);

	}
	
}
