package com.example.reactboot.modules.test;

import com.example.reactboot.common.response.JsonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "TEST", description = "API 테스트")
@RestController
@RequestMapping(value = "${api.data}/test")
public class HelloWorldController {
	@GetMapping("/hello")
	public JsonResponse<String> test() {
		return JsonResponse.ok("ok","Hello, Rect world!");
	}
}
