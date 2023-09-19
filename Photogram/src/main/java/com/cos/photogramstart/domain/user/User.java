package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//JPA - Java Persistence API (자바를 영구적으로 저장(DB) 할 수 있는 API를 제공)

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략이 데이터 베이스를 따라감
	private int id;  // 데이터가 들어갈 때 마다 번호를 매겨줄것임.
	
	@Column(length = 20, unique = true) //제약조건
	private String username; // 아이디
	@Column(nullable = false) //널 불가능
    private String password; // 비밀번호
	@Column(nullable = false)
    private String name; // 별명
    private String website; // 개인 웹사이트 주소
    private String bio; // 자기소개
    @Column(nullable = false)
    private String email; // 이메일
    private String phone; // 전화번호
    private String gender; // 성별
    
    private String profileImageUrl; // 프로필 사진
    private String role; // 권한 
    
    //양뱡향 매핑을 위한 image
    //나는 연관관계 주인이 아니야 테이블에 컬럼 만들지마.
    //User를 Select할때 해당 User id로 등록된 image들을 다 가져와.
    // Lazy = User를 Select 할때 해당 User id로 등록된 image들을 가져오지마 - 대신 getImages() 함수의 image들이 호출될때만 가져와
    // Eager = User를 Select 할 때 해당 User id로 등록된 image들을 전부 Join해서 가져와!
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) 
    private List<Image> images; //양방향 매핑
    
    private LocalDateTime createDate; // 데이터가 입력된 시간.
    
    @PrePersist //디비에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    
}
