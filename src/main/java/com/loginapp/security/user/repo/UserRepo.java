package com.loginapp.security.user.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loginapp.security.user.dataobjects.UserInfo;

public interface UserRepo extends JpaRepository<UserInfo, Long> {

	public UserInfo findByUsername(String username);
	@Transactional
	public void deleteByUsername(String username);
}
