package com.example.reactboot.modules.member.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Setter;

@Schema(description = "로그인 사용자 정보")
@Setter
public class MemberLoginVo {
    @Schema(
            description = "회원 idx",
            required = true
    )
    public int memberIdx;

    @Schema(
            description = "회원 id",
            required = true
    )
    public String memberId;

    @Schema(
            description = "회원 이름",
            required = true
    )
    public String memberNm;

    @Schema(
            description = "핸드폰 번호",
            required = true
    )
    public String tel;

    @Schema(
            description = "회원 유형",
            required = true
    )
    public String memberDiv;

    @Schema(
            description = "프로필 사진",
            required = true
    )
    public String img;

    @Schema(
            description = "회원 상태",
            required = true
    )
    public String memberStatus;
}
