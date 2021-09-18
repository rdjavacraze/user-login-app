package com.loginapp.security.auth.provider;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username=authentication.getName();
		String password=authentication.getCredentials().toString();
		if("admin".equalsIgnoreCase(username) && "admin".equalsIgnoreCase(password)){
			return new UsernamePasswordAuthenticationToken(username,password,Collections.EMPTY_LIST);
		}
		else{
			
			throw new BadCredentialsException("Exception Occured due to bad credentials");
		}
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		 return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	
}
