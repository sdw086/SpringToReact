package com.example.reactboot.modules.member.dto;

import com.example.reactboot.common.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinDto {
    private int memberIdx = 0;

    private String memberId = "";
    private String pwd = "";
    private String pwd1 = "";
    private String pwd2 = "";
    private String memberDiv = "";
    private String memberNm = "";
    private String img = "";
    private String tel = "";
    private String email;
    private String refreshToken = "";
    private int memberStatus = 1;
    private int createMember = 0;
    private int updateMember = 0;

    public MemberJoinDto(String email1, String email2) {
        if (!"".equals(email1) && !"".equals(email2))  {
            this.email = StringUtil.nvl((email1.concat("@")).concat(email2), "");
        } else {
            this.email      = "";
        }
    }
}
