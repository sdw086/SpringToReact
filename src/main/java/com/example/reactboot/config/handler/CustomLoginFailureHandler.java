package com.example.reactboot.config.handler;

import com.example.reactboot.common.utils.ScriptUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CustomLoginFailureHandler implements AuthenticationFailureHandler {
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		if (exception instanceof BadCredentialsException) {
			try {
				ScriptUtil.alertMsg(response, "아이디나 비밀번호가 맞지 않습니다. 다시 확인해 주세요.", "back");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				ScriptUtil.alertMsgGoUrl(response, "오류가 발생하였습니다.", "/login/login", request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
