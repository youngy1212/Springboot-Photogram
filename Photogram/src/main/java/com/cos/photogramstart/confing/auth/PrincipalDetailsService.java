package com.cos.photogramstart.confing.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service //IoC
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	//1. 패스워드는 알아서 체킹하니까 신경쓸 필요 없다.
	//2. 리턴이 정상적으로 되면 자동으로 UserDetails타입을 세션을 만든다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//username 체크해야함 password 는 시큐리티가 알아서 해줌
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) {
			return null;
		}else {
			return new PrincipalDetails(userEntity);
		}
		
		
	}

}
