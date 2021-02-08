package kr.co.paywith.pw.data.repository.mbs.mrhstSeat;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MrhstSeatService {

	@Autowired
	MrhstSeatRepository mrhstSeatRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public MrhstSeat create(MrhstSeat mrhstSeat) {

		  // 데이터베이스 값 갱신
		  MrhstSeat newMrhstSeat = this.mrhstSeatRepository.save(mrhstSeat);

		  return newMrhstSeat;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public MrhstSeat update(MrhstSeatUpdateDto mrhstSeatUpdateDto, MrhstSeat existMrhstSeat) {

		  // 입력값 대입
		  this.modelMapper.map(mrhstSeatUpdateDto, existMrhstSeat);

		  // 데이터베이스 값 갱신
		  this.mrhstSeatRepository.save(existMrhstSeat);

		  return existMrhstSeat;
	 }

}
