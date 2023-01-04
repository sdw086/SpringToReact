package com.example.reactboot.config;

import com.example.reactboot.config.handler.CustomLoginFailureHandler;
import com.example.reactboot.config.handler.CustomLoginSuccessHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
@Component
public class JsonSecurityFilter extends AbstractAuthenticationProcessingFilter {

	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";

	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

	public static final String HTTP_METHOD = "POST";

	private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/api/member/login",	HTTP_METHOD);

	@Autowired
	public JsonSecurityFilter(AuthenticationManager authenticationManager, CustomLoginSuccessHandler customLogInSuccessHandler, CustomLoginFailureHandler customLoginFailureHandler) {
		super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
		setAuthenticationSuccessHandler(customLogInSuccessHandler);
		setAuthenticationFailureHandler(customLoginFailureHandler);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		log.info("JsonUsernamePasswordAuthenticationFilter 이 동작합니다!!!");
		System.out.println("Method = " + request.getMethod());
		System.out.println("application = " + request.getContentType());
		if (!request.getMethod().equals(HTTP_METHOD) || !request.getContentType().equals("application/json")) {//POST가 아니거나 JSON이 아닌 경우
			log.error("POST 요청이 아니거나 JSON이 아닙니다!");
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String username = "admin";
		String password = "1qaz2wsx";

		if(username ==null || password == null){
			throw new AuthenticationServiceException("DATA IS MISS");
		}

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);//getAuthenticationManager를 커스텀해줌
	}

	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
	}
}
