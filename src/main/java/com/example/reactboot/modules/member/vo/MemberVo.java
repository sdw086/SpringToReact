package com.example.reactboot.modules.member.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberVo {

    @JsonIgnore
    private int totalCount;
    
    @Schema(description = "목록 순서")
    private int rowNum;

    @Schema(description = "회원 일련 번호")
    private int memberIdx;

    @Schema(description = "회원 아이디")
    private String memberId;

    @Schema(description = "회원 이름")
    private String memberNm;

    @Schema(description = "회원 상태")
    private int memberStatus;
}
