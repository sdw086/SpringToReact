package com.example.reactboot.modules.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUploadDto {

    @Schema(description = "원격 저장소 주소")
    private String fileUrl;
    @Schema(description = "파일 저장 경로")
    private String img;
}
