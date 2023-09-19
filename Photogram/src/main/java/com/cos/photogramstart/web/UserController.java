package com.cos.photogramstart.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.confing.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;

	@GetMapping("/user/{pageUserId}")
	public String profile(@PathVariable int pageUserId, Model model,@AuthenticationPrincipal PrincipalDetails principalDetails ) {
		UserProfileDto dto = userService.회원프로필(pageUserId,principalDetails.getUser().getId());
		
		model.addAttribute("dto",dto);
		return "user/profile";
	}
	
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		//세션에 있는 정보 접근하는 방법
		//1. 어노테이션을 활용(추천!)
		//System.out.println(principalDetails.getUser());
		//2. 직접 찾아가는법
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		PrincipalDetails mprincipalDetails = (PrincipalDetails)auth.getPrincipal();
//		System.out.println(mprincipalDetails.getUser());
		
		
		//model로 받아서 넘기는 방법. 대신 spring-security-taglibs 사용
		//model.addAttribute("principal",principalDetails.getUser());
		
		return "user/update";
	}
}
