package com.example.reactboot.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HttpsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;

        res.setHeader("Access-Control-Allow-Origin"     , "*");
        res.setHeader("Access-Control-Allow-Methods"    , "POST, GET, DELETE, PUT, PATCH, OPTIONS");
        res.setHeader("Access-Control-Max-Age"          , "3600");
        res.setHeader("Access-Control-Allow-Headers"    , "X-Requested-With, Origin, Content-Type, Accept, X-XSRF-TOKEN, Authorization");

        chain.doFilter(request, response);
    }
}
