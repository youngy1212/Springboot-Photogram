package com.cos.photogramstart.confing.oauth;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.confing.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuth2DetailService extends DefaultOAuth2UserService { // OAtuh2 user타입으로 만들어줘야함.

	// 파싱해온 정보를 유저정보에 넣어야함
	private final UserRepository userRepository;
	
	//private final BCryptPasswordEncoder bCryptPasswordEncoder;
	// 순환 오류 발생!!

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		Map<String, Object> userInfo = oAuth2User.getAttributes();
		String name = (String) userInfo.get("name"); // 오브젝트니 string으로 다운캐스팅
		String email = (String) userInfo.get("email");

		// username 없는데? 임의로 만들어줌
		String username = "facebook_" + (String) userInfo.get("id");
		String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()); // 패스워드로 로그인하는게 아니라 몰라도됨

		User userEntity = userRepository.findByUsername(username);

		if (userEntity == null) {// 최초 로그인
			User user = User.builder()
					.username(username)
					.password(password)
					.name(name)
					.email(email)
					.role("ROLE_USER")
					.build();

			return new PrincipalDetails(userRepository.save(user), oAuth2User.getAttributes());

		} else { // 이미 페이스북으로 회원가입
			return new PrincipalDetails(userEntity, oAuth2User.getAttributes()); // oAuth 로그인인지 아닌지 체크하기 위해
		}

	}

}
