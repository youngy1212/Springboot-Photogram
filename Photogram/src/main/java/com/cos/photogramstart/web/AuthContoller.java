package com.cos.photogramstart.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.web.dto.auth.SignupDto;

@Controller // 1. Ioc 등록 2. 파일을 리턴하는 컨트롤러
public class AuthContoller {
	
	private static final Logger log =  LoggerFactory.getLogger(AuthContoller.class);

	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	
	//회원가입버튼 -> /auth/signup -> /auth/signin 리턴
	//회원가입 클릭 X 아무것도 발동안함 -> why? CSRF 토큰이 실행중이라.
	
	@PostMapping("/auth/signup")
	public String signup(SignupDto signupDto) { //key = value (x-www-form-urlencoded 방식으로 들어옴)
		//회원가입 실행
		log.info(signupDto.toString());
		return "auth/signin"; //회원가입 완료 후 로그인 페이지로 이동
	}
	
}
