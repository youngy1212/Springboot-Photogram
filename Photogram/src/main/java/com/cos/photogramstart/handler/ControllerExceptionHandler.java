package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice 
public class ControllerExceptionHandler {
	
	@ExceptionHandler(CustomValidationException.class) //RuntimeException 다 가로채서 가져옴
	public String validationException(CustomValidationException e) {
		//CMRespDto, Script 비교
		//1. 클라이언트에게 응답할때는 Script 좋음
		//2. Ajax 통신 - CMRespDto
		//3. android - CMRespDto	
		return Script.back(e.getErrorMap().toString());
	}
}
