package com.example.reactboot.moduls.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "TEST", description = "API 테스트")
@RestController
@RequestMapping(value = "/api")
public class HelloWorldController {
	@GetMapping("/hello")
	public String test() {
		return "Hello, world!";
	}
}
