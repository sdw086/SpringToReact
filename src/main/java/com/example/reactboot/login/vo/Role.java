package com.example.reactboot.login.vo;

import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public enum Role {
      ROLE_HA("ROLE_HA"                 , "최고 관리자")
    , ROLE_PA("ROLE_PA"                 , "학부모")
    , ROLE_ST("ROLE_ST"                 , "학생")
    , ROLE_NO("ROLE_NO"                 , "접근 권한 없음");

    private final String key;
    private final String title;

    private static final String AUTH_HA      = "HA";
    private static final String AUTH_PA      = "PA";
    private static final String AUTH_ST      = "ST";

	private static final int MEMBER_STATUS   	= 1;      // 로그인 허옹

    Role(String key, String title) {
        this.key        = key;
        this.title      = title;
    }

    /**
     * 사용자 role key 찾기
     */
    public static String findRoleKey(String findRoleKey, int memberStatus) {
		return findRole(findRoleKey, memberStatus).key;
	}

	public static Role findRole(String memberAuth, int memberStatus) {
		if (MEMBER_STATUS < memberStatus) {
			return ROLE_NO;
		} else {
			if (AUTH_HA.equals(memberAuth)) {
				return ROLE_HA;
			}
			if (AUTH_PA.equals(memberAuth)) {
				return ROLE_PA;
			}
			if (AUTH_ST.equals(memberAuth)) {
				return ROLE_ST;
			}

		}

		throw new NoSuchElementException("The role doesn't exist");
    }
}
