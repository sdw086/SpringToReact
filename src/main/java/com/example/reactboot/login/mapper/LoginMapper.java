package com.example.reactboot.login.mapper;

import com.example.reactboot.login.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {

    /**
     * 회원 로그인
     */
    UserVo selectMemberLogin(String memberId);
}
