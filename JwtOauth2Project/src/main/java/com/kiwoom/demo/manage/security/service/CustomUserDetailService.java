package com.kiwoom.demo.manage.security.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	// 원래는 사용자 아이디 패스워드를 확인하는 로직이 들어 가야 함
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new User("user", "1234", new ArrayList<>());
	}

}
