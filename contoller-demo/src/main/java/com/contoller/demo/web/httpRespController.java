package com.contoller.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class httpRespController {

	@GetMapping("text")
	public String text() {
		return "a.txt";
		//왜 경로가 resources/static 안이야? 프레임워크기 때문(틀이 정해져있음)
		//일반 정적 파일들은 - resources/static 폴더 내부 위치 
	}
	
	@GetMapping("/mus")
	public String mus() {
		return "b"; //머스태치 템플릿 엔진 라이브러리 등록 완료 - templates 폴더 안에
		//.mustache을 놔두면 확장자 없이 파일명만 적으면 자동으로 찾아감
	}
	
	@GetMapping("/jsp")
	public String jsp() {
		return "c";  //jsp 엔진 사용 : scr/main/webapp 폴드가 디폴트 경로!
		// 스프링부트가 지원안해줘서 내가 설정해야함! yml 설정
		// /WEB-INF/views/c.jsp (ViewResolver)가 설정
	     
	}
	
	
}
