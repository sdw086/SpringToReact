package com.example.reactboot.config;

import com.example.reactboot.config.handler.CustomReactHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Value("${site.react.path}")
	private String[] SITE_REACT_PATH;

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/static/", "classpath:/templates/")
				.setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
	}

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry.addInterceptor(new CustomReactHandler())
				.addPathPatterns(SITE_REACT_PATH);
	}
}
