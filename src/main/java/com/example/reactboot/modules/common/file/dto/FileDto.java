package com.example.reactboot.modules.common.file.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDto {
	@Schema(description = "원격 저장소 주소")
	private String fileUrl;
	@Schema(description = "파일 저장 경로")
	private String filePath;
	@Schema(description = "THUMB 파일 저장 경로")
	private String fileThumbPath;
	@Schema(description = "파일 포맷")
	private String fileExt;
	@Schema(description = "원본 파일명")
	private String fileName;
	@Schema(description = "파일 사이즈")
	private String fileSize;
}
