package com.example.reactboot.config.handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		String errorMessage;

		if (exception instanceof BadCredentialsException) {
			errorMessage = "아이디 또는 비밀번호를 잘못 입력했습니다. \n 입력하신 내용을 다시 확인해 주세요.";
		} else {
			errorMessage = "오류가 발생되었습니다. 시스템 담당자에게 문의하시기 바랍니다.";
		}

		System.out.println("errorMessage = " + errorMessage);
		//request.getSession().setAttribute("errorMessage", errorMessage);
		//setDefaultFailureUrl("/member/login?error=true");
		super.onAuthenticationFailure(request, response, exception);
	}
}