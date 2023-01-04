package com.example.reactboot.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import javax.servlet.*;
import java.io.IOException;

public class JsonChinFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	}
}
