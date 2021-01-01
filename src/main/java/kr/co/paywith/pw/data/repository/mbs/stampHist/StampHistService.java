package kr.co.paywith.pw.data.repository.mbs.stampHist;

import java.time.ZonedDateTime;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StampHistService {

	@Autowired
	StampHistRepository stampHistRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public StampHist create(StampHist stampHist) {

		  // 데이터베이스 값 갱신
		  StampHist newStampHist = this.stampHistRepository.save(stampHist);

		  return newStampHist;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public StampHist update(StampHistUpdateDto stampHistUpdateDto, StampHist existStampHist) {

		  // 입력값 대입
		  this.modelMapper.map(stampHistUpdateDto, existStampHist);

		  // 데이터베이스 값 갱신
		  this.stampHistRepository.save(existStampHist);

		  return existStampHist;
	 }


	/**
	 * 스탬프 이력 취소 처리
	 */
	@Transactional
	public void delete(StampHist stamphist) {
		// 스탬프 이력 취소
		stamphist.setCancelRegDttm(ZonedDateTime.now());
		stampHistRepository.save(stamphist);

		// TODO 회원정보에 스탬프 취소 개수 반영(유효한 쿠폰 있다면 취소처리까지 해야 함)

	}

}
