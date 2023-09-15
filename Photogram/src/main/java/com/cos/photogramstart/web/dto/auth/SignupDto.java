package com.cos.photogramstart.web.dto.auth;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data //Getter, Setter 
public class SignupDto { //요청하는 Dto
	
	@Size(min = 2,max = 20) //2이상~20자 미만
	@NotBlank //공백금지
	private String username;
	@NotBlank 
    private String password;
	@NotBlank
    private String email;
	@NotBlank
    private String name;

    //메소드를 만들어 넣는것이 편함
    public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.build();
    }
    
}
