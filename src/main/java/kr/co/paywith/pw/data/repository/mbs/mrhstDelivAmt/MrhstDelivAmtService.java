package kr.co.paywith.pw.data.repository.mbs.mrhstDelivAmt;

import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MrhstDelivAmtService {

	@Autowired
  MrhstDelivAmtRepository mrhstDelivAmtRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public MrhstDelivAmt create(MrhstDelivAmt mrhstDelivAmt) {

		  // 데이터베이스 값 갱신
		  MrhstDelivAmt newMrhstDelivAmt = this.mrhstDelivAmtRepository.save(mrhstDelivAmt);

		  return newMrhstDelivAmt;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public MrhstDelivAmt update(MrhstDelivAmtUpdateDto mrhstDelivAmtUpdateDto, MrhstDelivAmt existMrhstDelivAmt) {

		  // 입력값 대입
		  this.modelMapper.map(mrhstDelivAmtUpdateDto, existMrhstDelivAmt);

		  // 데이터베이스 값 갱신
		  this.mrhstDelivAmtRepository.save(existMrhstDelivAmt);

		  return existMrhstDelivAmt;
	 }

}
