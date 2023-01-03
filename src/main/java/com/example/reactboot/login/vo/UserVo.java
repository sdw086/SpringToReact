package com.example.reactboot.login.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVo {
    private Role role;

    private int memberIdx;
    private String memberId;
    private String memberDiv;
    private String memberAuth;
    private String memberNm;
    private String tel;
    private String img;
    private int memberStatus;
    private String pwd;

    public void initRole() {
        this.role = Role.findRole(memberAuth, memberStatus);
    }
}
