package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomValidationException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service //1. IoC등록 2. 트랜잭션 관리
public class AuthService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional //write(insert, Update, Delete)할 때만
	public User 회원가입(User user) {
		//회원가입 진행
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole("ROLE_USER");
		
		
		//중복가입 체크
		User findUsername = userRepository.findByUsername(user.getUsername());
		if(findUsername == null) {
			User userEntity = userRepository.save(user);
			return userEntity;
		}else {
			throw new CustomValidationException("이미 존재하는 아이디 입니다.", null);
		}
		
		
	}
}
