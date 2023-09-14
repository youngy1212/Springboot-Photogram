package com.cos.photogramstart.web.dto.auth;

import lombok.Data;

@Data //Getter, Setter 
public class SignupDto { //요청하는 Dto
	
	private String username;
    private String password;
    private String email;
    private String name;

}
