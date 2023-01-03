package com.example.reactboot.modules.member.mapper;

import com.example.reactboot.login.vo.UserVo;
import com.example.reactboot.modules.member.dto.MemberDto;
import com.example.reactboot.modules.member.dto.MemberJoinDto;
import com.example.reactboot.modules.member.vo.MemberVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    /**
     * 회원 로그인
     */
    UserVo selectMemberLogin(String memberId);

    /**
     * refresh token 확인
     */
    String selectRefreshToken(String refreshToken);

    /**
     * 회원 목록 조회
     */
    List<MemberVo> selectMemberList(MemberDto memberDto);

    /**
     * 아이디, 닉네임 중복 확인
     */
    String selectMemberDuplicate(String memberId);

    /**
     * 회원 등록
     */
    int insertMember(MemberJoinDto MemberJoinDto);

    /**
     * 로그아웃
     */
    int updateMemberLogout(MemberDto memberDto);

    /**
     * last login & refresh token 저장
     */
    void updateLastLogin(String memberId, String memberIp, String refreshToken);
}
