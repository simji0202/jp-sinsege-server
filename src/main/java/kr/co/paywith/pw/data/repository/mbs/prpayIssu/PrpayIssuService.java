package kr.co.paywith.pw.data.repository.mbs.prpayIssu;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PrpayIssuService {

	@Autowired
	PrpayIssuRepository prpayIssuRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public PrpayIssu create(PrpayIssu prpayIssu) {

		  // 데이터베이스 값 갱신
		  PrpayIssu newPrpayIssu = this.prpayIssuRepository.save(prpayIssu);

		  return newPrpayIssu;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public PrpayIssu update(PrpayIssuUpdateDto prpayIssuUpdateDto, PrpayIssu existPrpayIssu) {

		  // 입력값 대입
		  this.modelMapper.map(prpayIssuUpdateDto, existPrpayIssu);

		  // 데이터베이스 값 갱신
		  this.prpayIssuRepository.save(existPrpayIssu);

		  return existPrpayIssu;
	 }

}
