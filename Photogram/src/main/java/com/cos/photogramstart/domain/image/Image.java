package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.subscribe.Subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image { //N, 1
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String caption; //오늘 나 너무 피곤해
	private String postImageUrl; //사진을 전송받아서 그 사진을 서버에 폴더에 저장 - DB에 그 저장된 경로를 Insert
	
	
	@JoinColumn(name = "userId")
	@ManyToOne
	private User user;  //1,1

	//이미지 좋아요
	
	//댓글 
	
	private LocalDateTime createDate;

	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
}
