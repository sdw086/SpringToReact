package com.example.reactboot.moduls.product;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class SinglePageAppController implements ErrorController {
	// error handling for react
	@RequestMapping(value = "/error")
	public String handleError() {
		System.out.println("================================");
		System.out.println("오류페이지 실행");
		System.out.println("================================");
		return "forward:/404";
	}
}
