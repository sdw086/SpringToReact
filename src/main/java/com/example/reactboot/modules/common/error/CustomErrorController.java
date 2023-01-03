package com.example.reactboot.modules.common.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
	// error handling for react
	@RequestMapping(value = "/error")
	public String handleError() {
		return "forward:/404";
	}
}
