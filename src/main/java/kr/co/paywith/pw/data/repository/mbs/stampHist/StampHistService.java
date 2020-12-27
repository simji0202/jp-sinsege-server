package kr.co.paywith.pw.data.repository.mbs.stampHist;

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

}
