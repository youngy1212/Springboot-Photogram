package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.domain.user.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final SubscribeRepository subscribeRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Value("${file.path}")
	private String uploadFolder;
	
	@Transactional
	public User 회원프로필사진변경(int principalId, MultipartFile profileImageFile) {
		
		//사진업로드와 동일
		UUID uuid = UUID.randomUUID(); 
		String imageFileName = uuid+"_"+ profileImageFile.getOriginalFilename(); 
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);

		try { 
			Files.write(imageFilePath, profileImageFile.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		User userEntity = userRepository.findById(principalId).orElseThrow(()->{
			throw new CustomApiException("유저를 찾을 수 없습니다");
		});
		
		userEntity.setProfileImageUrl(imageFileName);
		
		return userEntity;
	} //더티체킹으로 업데이트됨 
	
	@Transactional(readOnly = true)
	public UserProfileDto 회원프로필(int pageUserId, int principalId) { //로그인 유저가 아닌 볼 유저
		UserProfileDto dto = new UserProfileDto();
		
		//SELECT * FROM image WHERE userId = :userId;
		User userEntity = userRepository.findById(pageUserId).orElseThrow(()->{
			throw new CustomException("해당 프로필 페이지는 없는 페이지 입니다.");
		});
		
		dto.setUser(userEntity);
		dto.setPageOwnerState(pageUserId == principalId); //1은 페이지 주인 -1은 주인아님
		dto.setImageCount(userEntity.getImages().size());
		
		 // DTO에 구독정보 담기
        int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);
		
        dto.setSubscribeState(subscribeState == 1);
        dto.setSubscribeCount(subscribeCount);
        
        //1번 방법 좋아요 카운트 추가하기
        userEntity.getImages().forEach((image)->{
        	image.setLikeCount(image.getLikes().size());
        });
        
        //2번방법 .jsp에서 ${image.likes.size()} 로 적어도 가능
		
		return dto;
	}
	

	@Transactional
	public User 회원수정(int id, User user) {
		//1. 영속화
		//1. 무조건 찾았다. 걱정마 get() 2. 못찾았어 익셉션 발생시킬께 orElseThorw()  
		 User userEntity = 
			        userRepository.findById(id).orElseThrow(() -> {
			            return new CustomValidationApiException("찾을 수 없는 ID입니다.");
			                });

		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);

		//2. 영속화된 오브젝트를 수정 - 더티체킹(업데이트 완료)
		userEntity.setName(user.getName());
		userEntity.setPassword(user.getPassword());
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		
		//Oauth 추가로 비번 확인
		if(userEntity.getPassword() == null) {
			return userEntity;
		}else {
			userEntity.setPassword(encPassword);
			return userEntity;
		}
		
		
	}//더티체킹이 일어나서 업데이트가 완료됨.


}
