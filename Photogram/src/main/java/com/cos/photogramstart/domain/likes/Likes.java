package com.cos.photogramstart.domain.likes;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.subscribe.Subscribe;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = { 
		@UniqueConstraint(
				name = "likes_uk", 
				columnNames = { "imageId", "userId" }
		)
})
public class Likes { //마리아 db는 like가 키워드임
	//N 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	

	
	@JoinColumn(name="imageId")
	@ManyToOne
	private Image image;//1  하나의 이미지에 여러개의 좋아요
	
	//무한참조됨 오류가 터짐
	// user -> images -> likes -> user 무한참조 -> images 가져지 않겠다 정의
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name="userId")
	@ManyToOne
	private User user; //1 
	
	private LocalDateTime createDate;

	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
}
