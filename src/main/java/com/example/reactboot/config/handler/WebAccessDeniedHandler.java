package com.example.reactboot.config.handler;

import com.example.reactboot.login.vo.Role;
import com.example.reactboot.login.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class WebAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException ade)
            throws IOException, ServletException {
        res.setStatus(HttpStatus.FORBIDDEN.value());

        if(ade instanceof AccessDeniedException) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                UserVo userVo = (UserVo) authentication.getPrincipal();

                Role roleTypes = Role.findRole(userVo.getMemberDiv(), userVo.getMemberStatus());
                if(!roleTypes.equals("ROLE_ADMIN")) {
                    req.setAttribute("msg", "접근권한 없는 사용자입니다.");
                    req.setAttribute("nextPage", "/main");

                } else {
                    req.setAttribute("msg", "로그인 권한이 없는 아이디입니다.");
                    req.setAttribute("nextPage", "/login/login");
                    res.setStatus(HttpStatus.UNAUTHORIZED.value());
                    SecurityContextHolder.clearContext();
                }
            }
        } else {
            logger.info(ade.getClass().getCanonicalName());
        }
        req.getRequestDispatcher("/err/denied-page").forward(req, res);
    }
}
