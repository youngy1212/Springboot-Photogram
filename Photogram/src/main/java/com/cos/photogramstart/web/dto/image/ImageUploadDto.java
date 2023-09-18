package com.cos.photogramstart.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ImageUploadDto { 
	//file(img)과 caption(사진설명)을 받음
	private MultipartFile file;
	private String caption;
}
