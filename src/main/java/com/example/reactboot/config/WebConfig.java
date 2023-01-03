package com.example.reactboot.config;

import com.example.reactboot.config.handler.CustomReactHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/templates", "classpath:/static/")
				.setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
	}

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry.addInterceptor(new CustomReactHandler())
				.addPathPatterns("/loginPage", "/hello", "/product/**");
		//	.excludePathPatterns("/", "/static/**", "/*.ico", "/css/**", "/js/**", "/error", "/index.html"
		//			, "/swagger-ui/**", "/api-docs/**", "/swagger-ui.html"
		//			, "/api/**");
	}
}
