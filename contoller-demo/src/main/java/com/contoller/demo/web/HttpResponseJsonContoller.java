package com.contoller.demo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contoller.demo.domain.User;

@RestController
public class HttpResponseJsonContoller {

	@GetMapping("/resp/json/javaobject")
	public User respJsonObject() {
		User user = new User();
		user.setUsername("윤지영");
		
		return user; //MessageConver가 자동으로 JavaObject를 json으로 변환
		//2. @RestController일때만 MessageConver가 작동
	}
}
