package com.cos.photogramstart.confing.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails,OAuth2User {

	private static final long serialVersionUID = 1L;

	private User user;
	private Map<String, Object> attributes;
	
	public PrincipalDetails (User user) {
		this.user = user;
	}

	public PrincipalDetails(User user,Map<String, Object> attributes) {
		this.user = user;
	}

	// 권한 : 한개가 아닐 수 있음.(3개 이상의 권한 Collection 사용)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 권한을 가져옴
		Collection<GrantedAuthority> collector = new ArrayList<>();
		collector.add(() -> {
			return user.getRole();
		});
		return collector;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() { // 만료니?
		return true; // 아니!
	}

	@Override
	public boolean isAccountNonLocked() { // 잠겼니?
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { // 비밀번호 만기됐니?
		return true;
	}

	@Override
	public boolean isEnabled() { // 활성화되어있니?
		return true;
	}

	
	//OAuth 추가시 
	@Override
	public Map<String, Object> getAttributes() {
		return attributes; //{id ; 23224, name:김나나,.....}
	}

	@Override
	public String getName() {
		return (String)attributes.get("name");
	}

}
