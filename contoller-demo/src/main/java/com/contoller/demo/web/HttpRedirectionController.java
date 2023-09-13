package com.contoller.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HttpRedirectionController {

	@GetMapping("/home")
	public String home() {
		//1만줄 가정
		return "home"; 
		//200번대 : 잘 요청됐어! 
	}
	
	@GetMapping("/away")
	public String away() {
		//다른코드
		return "redirect:/home"; //리다이렉션이 된다.
		//@Controller 에서만 작동
		//300번대 : 재요청 됐어 
	}
}
