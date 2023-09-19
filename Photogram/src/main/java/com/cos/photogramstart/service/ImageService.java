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
	
	//6. 이미지가 저장되는 경로 호출하기
	@Value("${file.path}")
	private String uploadFolder; //yml에 적어놓은 파일 경로 가져오기
	
	private final ImageRepository imageRepository;
	
	@Transactional
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails ) {
		
		//2. UUID 객체를 생성한다.
		UUID uuid = UUID.randomUUID(); 
		//1. 업로드 되는 원본 파일명을 imageFileName 라고 지정한다. ex)1.jpg
		//3. UUID를 더한 값으로 지정한다.
		String imageFileName = uuid+"_"+ imageUploadDto.getFile().getOriginalFilename(); 
		//4. UUID가 적용된 파일명 확인하기 sysout
		//5. image 저장 경로 지정하기
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		//결국 경로가 C:/project/Springboot-Photogram/upload/imageFileName(변경된이름)가 되는것!
		
		//7. 파일 업로드하기
		//1. 통신 2. I/O -> 예외가 발생할 수 있다. *런타임에만 잡아낼 수 있음 * 예외처리!
		try { 
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//8. image 파일 경로를 DB에 INSERT 하기
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
		imageRepository.save(image);
		
		//System.out.println(imageEntity.toString());  //toString 무한참조 오류

	}
		
}
