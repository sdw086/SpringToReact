package com.example.reactboot.login.vo;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
public class LoginAccount extends User {

    private final UserVo userVo;

    public LoginAccount(UserVo userVo) {
        super(userVo.getMemberId(), userVo.getPwd(), Collections.singletonList(new SimpleGrantedAuthority(Role.findRoleKey(userVo.getMemberAuth(), userVo.getMemberStatus()))));
        this.userVo = userVo;
        this.userVo.initRole();
    }
}
