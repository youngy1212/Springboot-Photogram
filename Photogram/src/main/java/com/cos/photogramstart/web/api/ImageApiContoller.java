package com.cos.photogramstart.web.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.confing.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.service.LikesService;
import com.cos.photogramstart.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ImageApiContoller {
	
	private final ImageService imageService;
	private final LikesService likesService;
	
	@GetMapping("api/image")
	public ResponseEntity<?> imageStory(
			@AuthenticationPrincipal PrincipalDetails principalDetails,
			@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
			){
		Page<Image> Images = imageService.이미지스토리(principalDetails.getUser().getId(),pageable);
		return new ResponseEntity<>(new CMRespDto<>(1,"성공",Images),HttpStatus.OK);
	}

	//좋아요 API
	@PostMapping("api/image/{imageId}/likes")
	public ResponseEntity<?> likes(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int imageId
			){
		likesService.좋아요(imageId,principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1, "성공",null), HttpStatus.OK );
	}
	
	@DeleteMapping("api/image/{imageId}/likes")
	public ResponseEntity<?> unlikes(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int imageId
			){
		likesService.좋아요취소(imageId,principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1, "취소 성공",null), HttpStatus.OK );
	}
	
}
