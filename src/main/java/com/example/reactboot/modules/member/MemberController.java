package com.example.reactboot.modules.member;

import com.example.reactboot.common.response.JsonResponse;
import com.example.reactboot.modules.member.dto.MemberDto;
import com.example.reactboot.modules.member.dto.MemberJoinDto;
import com.example.reactboot.modules.member.dto.ProfileUploadDto;
import com.example.reactboot.modules.member.service.MemberService;
import com.example.reactboot.modules.member.vo.MemberListVo;
import com.example.reactboot.modules.member.vo.MemberLoginVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Tag(name = "MEMBER", description = "학부모 회원 관리")
@CrossOrigin(origins = "${api.token.parents-url}, ${api.token.student-url}")
@RestController
@RequestMapping(value = "${api.data}/member")
public class MemberController {
	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	/**
	 * 회원 로그인
	 */
	@Operation(summary = "회원 로그인", description = "로그인 처리 및 회원 정보 전달", responses = {
			@ApiResponse(responseCode = "200", description = "로그인 성공")
			, @ApiResponse(responseCode = "500", description = "로그인 오류", content = @Content(schema = @Schema(implementation = JsonResponse.class)))
	})
	@PostMapping(value = "/login")
	public JsonResponse<MemberLoginVo> memberLogin(HttpServletRequest request, HttpServletResponse response
			, @Parameter(description = "아이디", name = "username", required = true) @RequestParam(value = "username", defaultValue = "") String username
			, @Parameter(description = "비밀번호 [AES128 암화화]", name = "passwd", required = true) @RequestParam(value = "passwd", defaultValue = "") String passwd
	) throws Exception {
		System.out.println("================================");
		System.out.println("username : " + username);
		System.out.println("passwd : " + passwd);
		System.out.println("================================");

		MemberDto memberDto 				= new MemberDto();
		memberDto.setUsername(username);
		memberDto.setPwd(passwd);

		MemberLoginVo memberLoginVo         = memberService.selectMemberLogin(memberDto);

		try {
			if (memberLoginVo.memberIdx > 0) {
				return JsonResponse.ok("로그인에 성공하였습니다.", memberLoginVo);
			} else {
				return JsonResponse.error("오류가 발생되었습니다. 시스템 담당자에게 문의하시기 바랍니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.error("오류가 발생되었습니다. 시스템 담당자에게 문의하시기 바랍니다.");
		}
	}

	/**
	 * 로그아웃
	 */
	@Operation(summary = "로그아웃", description = "로그아웃", responses = {
			@ApiResponse(responseCode = "200", description = "로그아웃 성공")
			, @ApiResponse(responseCode = "500", description = "로그아웃 에러", content = @Content(schema = @Schema(implementation = JsonResponse.class)))
	})
	@PostMapping(value = "/logout")
	public JsonResponse logout(
			@Parameter(description = "member IDX", name = "memberIdx", required = true) @RequestParam(value = "memberIdx", defaultValue = "") int memberIdx
			, @Parameter(description = "member ID", name = "memberId", required = true) @RequestParam(value = "memberId", defaultValue = "") String memberId
	) {
		MemberDto memberDto 				= new MemberDto();
		memberDto.setMemberIdx(memberIdx);
		memberDto.setMemberId(memberId);

		try {
			int resultCode                  = memberService.updateMemberLogout(memberDto);

			if (resultCode > 0) {
				return JsonResponse.ok("정상적으로 로그아웃 되었습니다.");
			} else {
				return JsonResponse.error("오류가 발생되었습니다. 시스템 담당자에게 문의하시기 바랍니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.error("오류가 발생되었습니다. 시스템 담당자에게 문의하시기 바랍니다.");
		}
	}

	/**
	 * 회원 목록
	 */
	@Operation(summary = "회원목록", description = "type별 회원 목록", responses = {
			@ApiResponse(responseCode = "200", description = "회원 조회 성공")
			, @ApiResponse(responseCode = "401", description = "인증안됨", content = @Content(schema = @Schema(implementation = JsonResponse.class)))
			, @ApiResponse(responseCode = "500", description = "회원 조회 실패", content = @Content(schema = @Schema(implementation = JsonResponse.class)))
	})
	@PostMapping(value = "/list")
	public JsonResponse<MemberListVo> selectMemberList(
			@Parameter(description = "페이지", name = "page", required = true) @RequestParam(value = "page", defaultValue = "1") int currentPageNo
			, @Parameter(description = "회원 상태", name = "searchStatus", required = true) @RequestParam(value = "searchStatus", defaultValue = "Y") int searchStatus
			, @Parameter(description = "검색 컬럼", name = "searchFiled") @RequestParam(value = "searchField", required = false, defaultValue = "") String searchField
			, @Parameter(description = "검색어", name = "searchWord") @RequestParam(value = "searchWord", required = false, defaultValue = "") String searchWord
			, @Parameter(description = "회원 유형(HA, HB, HC, HD, BA, BB, FA, FB, ST, PA)", name = "memberDiv") @RequestParam(value = "memberDiv", defaultValue = "") String memberDiv
	) {
		try {
			MemberDto memberDto 			= new MemberDto();
			memberDto.setMemberDiv(memberDiv);
			memberDto.setSearchStatus(searchStatus);
			memberDto.setSearchField(searchField);
			memberDto.setSearchWord(searchWord);
			memberDto.setCurrentPageNo(currentPageNo);

			MemberListVo memberListVo		= memberService.selectMemberList(memberDto);

			return JsonResponse.ok("회원 목록 조회 성공", memberListVo);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.error("오류가 발생되었습니다. 시스템 담당자에게 문의하시기 바랍니다.");
		}
	}

	/**
	 * 회원 가입
	 */
	@Operation(summary = "학부모 회원 가입", description = "학부모 회원 가입", responses = {
			@ApiResponse(responseCode = "200", description = "학부모 회원 가입 성공"),
			@ApiResponse(responseCode = "404", description = "학부모 회원 가입 오류", content = @Content(schema = @Schema(implementation = JsonResponse.class)))
	})
	@PostMapping(value = "/memberJoin")
	public  JsonResponse<String> memberWriteProc(
			@Parameter(description = "아이디", name = "memberId", required = true) @RequestParam(value = "memberId", defaultValue = "") String memberId,
			@Parameter(description = "이름", name = "memberNm", required = true) @RequestParam(value = "memberNm", defaultValue = "") String memberNm,
			@Parameter(description = "회원 권한", name = "memberDiv", required = true) @RequestParam(value = "memberDiv", defaultValue = "") String memberDiv,
			@Parameter(description = "비밀번호1", name = "pwd1") @RequestParam(value = "pwd1", required = false, defaultValue = "") String pwd1,
			@Parameter(description = "비밀번호2", name = "pwd2") @RequestParam(value = "pwd2", required = false,  defaultValue = "") String pwd2,
			@Parameter(description = "휴대폰 번호", name = "tel", required = true) @RequestParam(value = "tel", defaultValue = "") String tel,
			@Parameter(description = "이메일1", name = "email1", required = true) @RequestParam(value = "email1", defaultValue = "") String email1,
			@Parameter(description = "이메일2", name = "email2", required = true) @RequestParam(value = "email2", defaultValue = "") String email2,
			@Parameter(description = "프로필 사진", name = "img") @RequestParam(required = false, value = "img", defaultValue = "") String img
	) {
		try {
			MemberJoinDto memberJoinDto 	= new MemberJoinDto(email1, email2);
			memberJoinDto.setMemberId(memberId);
			memberJoinDto.setMemberNm(memberNm);
			memberJoinDto.setPwd1(pwd1);
			memberJoinDto.setPwd2(pwd2);
			memberJoinDto.setTel(tel);
			memberJoinDto.setMemberDiv(memberDiv);
			memberJoinDto.setImg(img);

			int resultCode          		= memberService.insertMember(memberJoinDto);

			if (resultCode < 0) {
				if (resultCode == -1) {
					return JsonResponse.error("비밀번호가 일치하지 않습니다.");
				} else if (resultCode == -2) {
					return JsonResponse.error("아이디가 중복 되었습니다.");
				} else if (resultCode == -3) {
					return JsonResponse.error("닉네임이 중복 되었습니다.");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.error("오류가 발생되었습니다. 시스템 담당자에게 문의하시기 바랍니다.");
		}

		return JsonResponse.ok("학부모 회원 가입이 완료 되었습니다.");
	}

	/**
	 * 프로필 사진 등록
	 */
	@Operation(summary = "프로필 사진 등록", description = "회원 가입시 프로필 사진 등록, 저장 경로 리턴해 주기 ", responses = {
			@ApiResponse(responseCode = "200", description = "프로필 사진 등록 성공")
			, @ApiResponse(responseCode = "404", description = "프로필 사진 등록 에러", content = @Content(schema = @Schema(implementation = JsonResponse.class)))
	})
	@PostMapping(value = "/profileUpload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public JsonResponse<ProfileUploadDto> profileUpload(
			@Parameter(description = "프로필 이미지", name = "file", required = true) @RequestParam(value = "file", defaultValue = "") MultipartFile file
			, @Parameter(description = "썸네일 작성 여부", name = "thumbnailYn") @RequestParam(value = "thumbnailYn", required = false, defaultValue = "Y") String thumbnailYn
	) {
		try {

			ProfileUploadDto profileUploadDto      	= memberService.uploadProfile(file, thumbnailYn);
			return JsonResponse.ok("프로필 사진이 업로드 되었습니다.", profileUploadDto);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.error("오류가 발생되었습니다. 시스템 담당자에게 문의하시기 바랍니다.");
		}
	}
}
