package com.loginapp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.loginapp.security.user.dataobjects.UserInfo;
import com.loginapp.security.user.repo.UserRepo;

@SpringBootApplication
public class LoginappApplication {

	@Autowired
	private UserRepo repo;
	
	public static void main(String[] args) {
		SpringApplication.run(LoginappApplication.class, args);
	}

	
	@PostConstruct
	public void pushUserInfo(){
		UserInfo userInfoForAdmin=new UserInfo("admin", "admin");
		UserInfo userInfoForUser=new UserInfo("user", "user");
		List<UserInfo> userInfoList=new ArrayList<>();
		userInfoList.add(userInfoForUser);
		userInfoList.add(userInfoForAdmin);
		repo.saveAll(userInfoList);
	}
}
