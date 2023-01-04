package com.example.reactboot.modules.common.file;

import com.example.reactboot.common.response.JsonResponse;
import com.example.reactboot.modules.common.file.dto.FileDto;
import com.example.reactboot.modules.common.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Tag(name = "FILE MANAGER", description = "첨부파일 업로드")
@CrossOrigin(origins = "${api.token.parents-url}, ${api.token.student-url}")
@RestController
@RequestMapping(value = "${api.data}/file")
public class FileController {

	private final FileService fileService;

	public FileController(FileService fileService) {
		this.fileService = fileService;
	}

	/**
	 * 첨부파일 업로드
	 */
	@Operation(summary = "파일 업로드", description = "파일 저장 공통 모듈", responses = {
			@ApiResponse(responseCode = "200", description = "첨부 파일 업로드 성공"),
			@ApiResponse(responseCode = "404", description = "첩무 파일 업로드 실패", content = @Content(schema = @Schema(implementation = JsonResponse.class)))
	})
	@PostMapping(value = "/fileUpload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public JsonResponse<FileDto> fileUpload(
			@Parameter(description = "첨부 파일", name = "file", required = true) @RequestParam(value = "file", defaultValue = "") MultipartFile file,
			@Parameter(description = "저장 타입-영문 대문자 ex)회원관련 MEMBER, 게시판 BOARD", name = "uploadDir", required = true) @RequestParam(value = "uploadDir", defaultValue = "") String uploadDir,
			@Parameter(description = "썸네일 작성 여부", name = "thumbnailYn", required = true) @RequestParam(value = "thumbnailYn", defaultValue = "N") String thumbnailYn
	) {
		try {
			Map<String, Object> fileData        	= fileService.uploadFile(file, StringUtils.upperCase(uploadDir), thumbnailYn);

			if (Integer.parseInt(fileData.get("resultCode").toString()) == 1) {
				FileDto fileDto           			= new FileDto();
				fileDto.setFileUrl(fileData.get("fileUrl").toString());
				fileDto.setFilePath(fileData.get("filePath").toString());
				fileDto.setFileThumbPath(fileData.get("fileThumbPath").toString());
				fileDto.setFileExt(fileData.get("fileFormat").toString());
				fileDto.setFileName(fileData.get("originalFileName").toString());
				fileDto.setFileSize(fileData.get("fileSize").toString());

				return JsonResponse.ok("파일 업로드 성공하였습니다.", fileDto);
			} else if (Integer.parseInt(fileData.get("resultCode").toString()) == -1) {
				return JsonResponse.error("파일이 없습니다.");
			} else if (Integer.parseInt(fileData.get("resultCode").toString()) == -4) {
				return JsonResponse.error("최대 업로드 사이즈는 " + fileData.get("maxFileSize") + " MB 입니다.");
			} else {
				return JsonResponse.error("오류가 발생되었습니다. ("+ fileData.get("resultCode") +")");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.error("오류가 발생되었습니다. 시스템 담당자에게 문의하시기 바랍니다.");
		}
	}
}
