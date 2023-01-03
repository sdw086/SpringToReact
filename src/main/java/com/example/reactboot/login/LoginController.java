package com.example.reactboot.login;

import com.example.reactboot.common.annotation.Noheader;
import com.example.reactboot.login.vo.CurrentAccount;
import com.example.reactboot.login.vo.UserVo;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Hidden
@Controller
@RequestMapping("/member")
public class LoginController {

    /**
     * 로그인 페이지
     */
    @Noheader
    @GetMapping(value = "/login")
    public String login(@CurrentAccount UserVo userVo) {
        if (userVo != null) {
            return  "redirect:/swagger-ui/index.html";
        }

        return "/member/login";
    }
}
