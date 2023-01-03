package com.example.reactboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    @Value("${api.token.student-url}")
    private String studentUrl;

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request      = (HttpServletRequest) req;
        HttpServletResponse response    = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin"        , studentUrl);
        response.setHeader("Access-Control-Allow-Credentials"   , "true");
        response.setHeader("Access-Control-Allow-Methods"       ,"GET, POST");
        response.setHeader("Access-Control-Max-Age"             , "3600");
        response.setHeader("Access-Control-Allow-Headers"       , "Origin,Accept, X-Requested-With, Content-Type,Access-Control-Request-Method, Access-Control-Request-Headers, Authorization, RefreshToken");

        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        }else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}
