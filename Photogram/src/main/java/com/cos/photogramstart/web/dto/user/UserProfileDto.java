package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {

	private boolean pageOwnerState;
	private int imageCount;
	//view 페이지 말고 최대한 뒤쪽에서 연산 다해서 넘기는게 좋음
	private User user;
}
