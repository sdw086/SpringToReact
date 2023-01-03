package com.example.reactboot.config;

import com.example.reactboot.config.handler.CustomAccessDeniedHandler;
import com.example.reactboot.config.handler.CustomLoginFailureHandler;
import com.example.reactboot.config.handler.WebAccessDeniedHandler;
import com.example.reactboot.login.service.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginService loginService;
    private final WebAccessDeniedHandler webAccessDeniedHandler;
    private final static String[] allowedUrls = {"/", "/error", "/index.html", "/loginPage", "/hello", "/product/**", "/api/**"};

    public SecurityConfig(LoginService loginService, WebAccessDeniedHandler webAccessDeniedHandler) {
        this.loginService = loginService;
        this.webAccessDeniedHandler = webAccessDeniedHandler;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**", "/*.ico", "/css/**", "/js/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
         http
                 .authorizeRequests()
                 .antMatchers(allowedUrls).permitAll()
                 .antMatchers("/main/", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**").hasRole("HA")
                 .anyRequest().authenticated()
            .and()
                 .formLogin().defaultSuccessUrl("/swagger-ui.html", true)
                 .usernameParameter("username").passwordParameter("password")
            .and()
                 .logout().invalidateHttpSession(true).deleteCookies("JSESSIONID")
            .and().exceptionHandling().accessDeniedHandler(webAccessDeniedHandler)
            .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler())
            .and()
                 .authenticationProvider(authenticationProvider())
            .csrf()
                 .ignoringAntMatchers(allowedUrls)
                 .requireCsrfProtectionMatcher(new CsrfRequireMatcher())
                 .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService);
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() { return new CustomLoginFailureHandler(); }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(loginService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    static class CsrfRequireMatcher implements RequestMatcher {
        private static final Pattern ALLOWED_METHODS = Pattern.compile("^(GET|POST|HEAD|TRACE|OPTIONS)$");

        @Override
        public boolean matches(HttpServletRequest request) {
            if (ALLOWED_METHODS.matcher(request.getMethod()).matches())
                return false;
            return true;
        }
    }
}
