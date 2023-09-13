package com.contoller.demo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryPathContoller {

	@GetMapping("/chicken")
	public String chickenQuery(String type) {
		return type + "배달갑니다. (쿼리 스트링) ";
	}

	@GetMapping("/chicken/{type}")
	public String post(@PathVariable String type) {
		return type + "배달갑니다.(주소변수 매핑) ";
	}

}
