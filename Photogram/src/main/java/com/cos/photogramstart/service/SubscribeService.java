package com.cos.photogramstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {

	 private final SubscribeRepository subscribeRepository;
	 private final EntityManager em;//Repository는 EntityManager 를  구현해서 만들어져 있는 구현체
	 
	@Transactional
	public void 구독하기(int fromUserId, int toUserId){
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		} catch (Exception e) {
			throw new CustomApiException("이미 구독을 하였습니다.");
		}
	}
	
	@Transactional
	public void 구독취소하기(int fromUserId, int toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}

	@Transactional(readOnly = true)
	public List<SubscribeDto> 구독리스트(int principalId, int pageUserId) {
		
		//Repository 에서 네이티브 사용 불가능. Why? Subscribe 으로만 받을 수 있어서
		//여기서 직접 쿼리 만들어 주자.
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
		sb.append("if((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id),1,0) subscibeState, ");
		sb.append("if((? = u.id),1,0) equalUserState ");
		sb.append("FROM user u INNER JOIN subscribe s  ");
		sb.append("ON u.id = s.toUserId ");
		sb.append("WHERE s.fromUserId = ?");
		
		//끝에 한칸을 꼭 띄우기! + 세미콜론 들어가면 안됨.
		
		//fromUserId 의 물음표 : principalId
		//equalUserState 의 물음표 principalId 
		//마지막 물음표 : pageUserId
		
		//avax.persistence.Query
		//쿼리 완성
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1,principalId)
				.setParameter(2,principalId)
				.setParameter(3,pageUserId);
		
		//쿼리 실행 (qlrm 라이브러리 필요 = Dto에 DB 결과를 매핑하기 위해) 
		JpaResultMapper result = new JpaResultMapper();
		List<SubscribeDto>subscribeDtos = result.list(query, SubscribeDto.class);	
		//리턴타입이 여러개, SubscribeDto로 
		//qlrm DB에서 result 된 경과를 자바 class(DTO)에 맵핑해주는 라이브러리 
		
		return subscribeDtos;
	}
	
	
}
