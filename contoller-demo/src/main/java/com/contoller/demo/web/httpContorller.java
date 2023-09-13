package com.contoller.demo.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class httpContorller {

	@GetMapping("/get")
	public String get() {
		return "get 요청됨 ";
	}
	
	@PostMapping("/post")
	public String post() {
		return "post 요청됨 ";
	}
	
	@PutMapping("/put")
	public String put() {
		return "put 요청됨 ";
	}
	
	@DeleteMapping("/delet")
	public String delete() {
		return "delete 요청됨 ";
	}
}
