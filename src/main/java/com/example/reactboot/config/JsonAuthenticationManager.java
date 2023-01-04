package com.example.reactboot.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class JsonAuthenticationManager implements AuthenticationManager {
	private final UserDetailsService userDetailsService;

	public JsonAuthenticationManager(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("JsonAuthenticationManager 이 동작합니다!!!");
		UserDetails userDetails = userDetailsService.loadUserByUsername((String) authentication.getPrincipal());//username

		return new UsernamePasswordAuthenticationToken(userDetails
				, userDetails.getPassword()
				, userDetails.getAuthorities());
	}
}
