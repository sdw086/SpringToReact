package com.example.reactboot.moduls.common.handler;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomHandler implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		/* React로 가지말아야할 url patten */
		List<String> arrAntMatchUrl = new ArrayList<>(Arrays.asList(
				"api", "index.html", "api-docs", "swagger-ui", "swagger-ui.html"
		));

		String[] arrUrl = request.getServletPath().split("/");

		if (!"".equals(arrUrl[1])) {
			if (!arrAntMatchUrl.contains(arrUrl[1])) {
				System.out.println("================================");
				System.out.println("React go!!");
				System.out.println("================================");

				request.getRequestDispatcher("/").forward(request, response);

				return false;
			}
		}

		return true;
	}

}
