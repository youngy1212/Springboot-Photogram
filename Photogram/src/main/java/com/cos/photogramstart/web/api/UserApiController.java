package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.confing.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {// 데이터를 응답하는 애들 API컨트롤러라고 함.

	private final UserService userService;

	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(@PathVariable int id,
			@Valid UserUpdateDto userUpdateDto,
			BindingResult bindingResult, //꼭 @Valid가 적혀있는 다음 파라미터에 적어야함
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(bindingResult.hasErrors()) { 
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error: bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(),error.getDefaultMessage()); 
				System.out.println(error.getDefaultMessage());
			}
			throw new CustomValidationApiException("유효성 검사 실패함",errorMap);
		}else {
			User userEntity = userService.회원수정(id, userUpdateDto.toEntity()); // DB상 변경은 되지면 화면 변화X , 세션정보는 바뀌지 않았기 때문
			principalDetails.setUser(userEntity);
			return new CMRespDto<>(1, "회원수정 완료", userEntity);
		}
	}

}
