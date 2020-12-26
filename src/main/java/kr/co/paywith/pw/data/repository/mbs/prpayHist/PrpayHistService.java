package kr.co.paywith.pw.data.repository.mbs.prpayHist;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PrpayHistService {

	@Autowired
	PrpayHistRepository prpayHistRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public PrpayHist create(PrpayHist prpayHist) {

		  // 데이터베이스 값 갱신
		  PrpayHist newPrpayHist = this.prpayHistRepository.save(prpayHist);

		  return newPrpayHist;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public PrpayHist update(PrpayHistUpdateDto prpayHistUpdateDto, PrpayHist existPrpayHist) {

		  // 입력값 대입
		  this.modelMapper.map(prpayHistUpdateDto, existPrpayHist);

		  // 데이터베이스 값 갱신
		  this.prpayHistRepository.save(existPrpayHist);

		  return existPrpayHist;
	 }

}
