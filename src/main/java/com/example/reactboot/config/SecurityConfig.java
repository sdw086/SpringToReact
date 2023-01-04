package com.example.reactboot.config;

import com.example.reactboot.config.handler.CustomAccessDeniedHandler;
import com.example.reactboot.config.handler.CustomLoginFailureHandler;
import com.example.reactboot.config.handler.CustomLoginSuccessHandler;
import com.example.reactboot.login.service.LoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${site.security.unactive.path}")
    private String[] SITE_SECURITY_UNACTIVE_PATH;
    private final LoginService loginService;
    private final JsonSecurityFilter jsonSecurityFilter;

    public SecurityConfig(LoginService loginService, JsonSecurityFilter jsonSecurityFilter) {
        this.loginService = loginService;
        this.jsonSecurityFilter = jsonSecurityFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 패턴순서 작은 단위(세부 url) > 큰 단위(/member/**) 로 작성, 공통으로 사용하는 요소는 새부 url패턴 으로 작성 하여 url 사용할수 있게 한다.
        http.authorizeRequests()
                .antMatchers(SITE_SECURITY_UNACTIVE_PATH).permitAll()
                .antMatchers( "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**").hasRole("HQ")
                .anyRequest().authenticated();
        http.csrf().disable();

        http.formLogin()         // 로그인 페이지
                .successHandler(successHandler())   // 로그인 핸들러
                .failureHandler(failureHandler())   // 로그인 실패시 핸들러
                .permitAll();

        http.addFilterBefore(jsonSecurityFilter, UsernamePasswordAuthenticationFilter.class); // swagger go

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))   // 로그아웃 페이지
                .invalidateHttpSession(true);

        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());

        http.headers(headers -> headers.cacheControl(HeadersConfigurer.CacheControlConfig::disable));
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService);
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomLoginSuccessHandler(loginService);
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() { return new CustomLoginFailureHandler(); }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement()
            .sessionFixation().changeSessionId()
            .maximumSessions(1)
            .maxSessionsPreventsLogin(false)
            .sessionRegistry(new SessionRegistryImpl());
        return http.build();
    }
}
