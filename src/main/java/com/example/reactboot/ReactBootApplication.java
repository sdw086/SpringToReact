package com.example.reactboot;

import com.example.reactboot.config.SessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpSessionListener;

@SpringBootApplication
public class ReactBootApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ReactBootApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ReactBootApplication.class);
    }

    @Bean
    public HttpSessionListener httpSessionListener() {
        return new SessionListener();
    }
}
