package com.example.reactboot.config.handler;

import com.example.reactboot.login.service.LoginService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
	private final LoginService loginService;

	public CustomLoginSuccessHandler(LoginService loginService) {
		this.loginService = loginService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException {
		List<String> role = new ArrayList<>();
		auth.getAuthorities().forEach(authority -> role.add(authority.getAuthority()));

		// 권한별 로그인 설정
		if (role.contains("ROLE_HQ")) {
			response.sendRedirect("/swagger-ui.html");
		}
	}
}
