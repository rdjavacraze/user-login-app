package com.loginapp.security.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.loginapp.security.jwt.util.JWTAuthorizationUtil;
import com.loginapp.security.user.service.SimpleUserDetailsService;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	JWTAuthorizationUtil util;
	
	@Autowired
	SimpleUserDetailsService service;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		System.out.println("-----Request URI------------>"+request.getRequestURI());
		
		if(authHeader==null && "/api/dashboard".equalsIgnoreCase(request.getRequestURI())){
			authHeader=util.generateToken(request.getParameter("username"));
		}
		System.out.println(authHeader);
		if (authHeader!=null && !authHeader.isEmpty()) {
		authHeader = authHeader.replace("Bearer", "").trim();
		
			String username = util.getUserNameFromToken(authHeader);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = service.loadUserByUsername(username);
				username=request.getParameter("username");
				String password=request.getParameter("password");
				if (userDetails != null && 
						userDetails.getUsername().equalsIgnoreCase(username) && password.equalsIgnoreCase(userDetails.getPassword())
						&&
						util.validateToken(authHeader, userDetails)) {
					
					
					UsernamePasswordAuthenticationToken unamepwdAuthToken=
							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					
					unamepwdAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(unamepwdAuthToken);
				}
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
