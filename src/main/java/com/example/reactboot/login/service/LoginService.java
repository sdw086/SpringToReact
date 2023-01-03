package com.example.reactboot.login.service;

import com.example.reactboot.login.mapper.LoginMapper;
import com.example.reactboot.login.vo.LoginAccount;
import com.example.reactboot.login.vo.Role;
import com.example.reactboot.login.vo.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Log4j2
public class LoginService implements UserDetailsService {

    private final LoginMapper loginMapper;

    /**
     * 로그인 및 토큰 활성화
     */
    public void login(UserVo userVo) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new LoginAccount(userVo),
                userVo.getPwd(),
                Collections.singletonList(new SimpleGrantedAuthority(Role.findRoleKey(userVo.getMemberDiv(), userVo.getMemberStatus()))));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    /**
     * memberId를 이용한 로그인 처리
     */
    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        UserVo member = loginMapper.selectMemberLogin(memberId);

        if (member == null) {
            throw new UsernameNotFoundException(memberId);
        }

        return new LoginAccount(member);
    }
}
