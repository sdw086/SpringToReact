package com.example.reactboot.modules.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private int memberIdx = 0;

    private String memberId = "";
    private String memberNm = "";
    private String memberDiv = "";
    private String pwd = "";
    private String username = "";

    private int searchStatus = 1;
    private String searchUseYn = "";
    private String searchField = "";
    private String searchWord = "";

    private int currentPageNo = 1;
    private int recordCountPerPage = 10;
    private int paginationSize = 10;
    private int firstRecordIndex;
    private int lastRecordIndex;
}
