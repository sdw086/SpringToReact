package com.example.reactboot.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class JsonResponse<T> {
	@Schema(description = "응답 코드 [200:응답, 500:응답 오류]")
	private int code;

	@Schema (description = "응답 매시지")
	@JsonProperty("msg")
	private String message;

	private T data;

	public JsonResponse(int code, String message, T data) {
		this.code 		= code;
		this.message 	= message;
		this.data 		= data;
	}

	public JsonResponse(int code, String message) {
		this.code 		= code;
		this.message 	= message;
	}

	public static <T> JsonResponse<T> ok(String message) {
		return (new JsonResponse<>(200, message));
	}
	public static <T> JsonResponse<T> ok(String message, T data) {
		return (new JsonResponse<>(200, message, data));
	}

	public static <T> JsonResponse<T> error(String message) {
		return (new JsonResponse<>(500, message));
	}
}
