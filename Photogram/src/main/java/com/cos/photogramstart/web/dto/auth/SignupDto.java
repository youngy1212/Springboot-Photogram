package com.cos.photogramstart.web.dto.auth;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data //Getter, Setter 
public class SignupDto { //요청하는 Dto
	
	private String username;
    private String password;
    private String email;
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
