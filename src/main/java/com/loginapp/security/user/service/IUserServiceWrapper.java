package com.loginapp.security.user.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.loginapp.security.user.dataobjects.UserInfo;

public interface IUserServiceWrapper extends UserDetailsService {

	public List<UserInfo> findAllUsers();
	public void createOrUpdateUsers(UserInfo userInfo);
	public void deleteByUsername(String username);
	public UserInfo findByUserName(String username);
}
