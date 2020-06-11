package com.mouzetech.ordersystem.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.mouzetech.ordersystem.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		try {
		return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch(Exception ex) {
			return null;
		}
	}
	
}
