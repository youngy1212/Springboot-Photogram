package com.contoller.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.contoller.demo.domain.User;

@Controller
public class JavaToJspController {

	@GetMapping("jsp/java/model")
	public String jspToJavaToModel(Model model) { //함수의 파라미터에 model을 선언
		User user = new User();
		user.setUsername("user2");
		
		model.addAttribute("username",user.getUsername()); //addAttribute전달만 하면됨
		
		return "c";
	}
}
