package com.mouzetech.ordersystem.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mouzetech.ordersystem.security.JWTUtil;
import com.mouzetech.ordersystem.security.UserSS;
import com.mouzetech.ordersystem.services.UserService;

@RestController
@RequestMapping(value="/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;

	@RequestMapping(value="/refresh_token", method=RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse res){
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		res.addHeader("Authorization", "Bearer "+token);
		res.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	
}
