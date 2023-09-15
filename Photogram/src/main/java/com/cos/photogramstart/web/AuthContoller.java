package com.cos.photogramstart.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor  //final 붙은 애들은 다 생성자 만들어줌 (final 필드 DI 할 때 사용)
@Controller // 1. Ioc 등록 2. 파일을 리턴하는 컨트롤러
public class AuthContoller {
	
	private static final Logger log =  LoggerFactory.getLogger(AuthContoller.class);

	private final AuthService authService;
	
	
	//@Controller 를 Ioc를 등록하려함 
//	public AuthContoller(AuthService authService) { //근데 뭘 넣어줘야하지?
//		this.authService = authService; //authService에 있네? 주입해줘! 
//	} but 이 방법 말고 더 간단한 방법이 있음.
	
	
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
		//User <- SignupDto 데이터를 넣는것!
		User user = signupDto.toEntity();
		log.info(user.toString());
		User userEntity = authService.회원가입(user);
		System.out.println(userEntity);
		return "auth/signin"; //회원가입 완료 후 로그인 페이지로 이동
	}
	
}
