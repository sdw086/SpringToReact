package com.example.reactboot.config;

import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
	@Value("${server.session.timeout}")
	private int sessionTimeOut;

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		httpSessionEvent.getSession().setMaxInactiveInterval(sessionTimeOut);
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

	}
}
