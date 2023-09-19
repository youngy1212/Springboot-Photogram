package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.confing.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {
	
	@Value("${file.path}")
	private String uploadFolder; //yml에 적어놓은 파일 경로 가져오기
	
	private final ImageRepository imageRepository;
	
	@Transactional
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails ) {
		
		UUID uuid = UUID.randomUUID(); //uuid
		String imageFileName = uuid+"_"+ imageUploadDto.getFile().getOriginalFilename(); //1.jpg
		System.out.println("이미지 파일 이름"+ imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		//결국 경로가 C:/project/Springboot-Photogram/upload/imageFileName(변경된이름)가 되는것!
		
		//1. 통신 2. I/O -> 예외가 발생할 수 있다. *런타임에만 잡아낼 수 있음 * 예외처리!
		try { 
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//image 테이블에 저장
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
		Image imageEntity = imageRepository.save(image);
		
		//System.out.println(imageEntity.toString());  //toString 무한참조 오류

	}
		
}
