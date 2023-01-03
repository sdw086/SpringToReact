package com.example.reactboot.modules.member.vo;

import lombok.Data;

@Data
public class MemberDetailVo {
    private int memberIdx;
    private String memberId;
    private String memberDiv;
    private String memberNm;
    private String img;
    private String tel;
    private String email;
    private int memberStatus;
}
