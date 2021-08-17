package com.loginapp.security.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.loginapp.security.user.dataobjects.UserInfo;
import com.loginapp.security.user.repo.UserRepo;

@Service
public class SimpleUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepo repo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username);
		UserInfo userInfo=repo.findByUsername(username);
		if(userInfo!=null){
		//return new SimpleUserDetails(userInfo.getUsername());
			String ROLE_PREFIX = "ROLE_";
			List authorities =new  ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + "ADMIN"));
			return new User(userInfo.getUsername(), userInfo.getPassword(), authorities);
			
		}
		else
			return null;
	}
	
	

}
