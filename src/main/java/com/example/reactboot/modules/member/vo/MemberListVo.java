package com.example.reactboot.modules.member.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberListVo {

	@Schema(description = "검색 필드")
	private String searchField;
	@Schema(description = "검색어")
	private String searchWord;
	@Schema(description = "현재 페이지")
	private int currentPageNo;
	@Schema(description = "회원 유형")
	private String memberDiv;
	@Schema(description = "전체 수")
	private int totalCount;
	@Schema(description = "현재 페이지 자료 수")
	private int dataListCount;
	@Schema(description = "회원 목록")
	private List<MemberVo> list;
}
