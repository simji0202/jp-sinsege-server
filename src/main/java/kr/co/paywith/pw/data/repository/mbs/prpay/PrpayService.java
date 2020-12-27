package kr.co.paywith.pw.data.repository.mbs.prpay;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PrpayService {

	@Autowired
	PrpayRepository prpayRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public Prpay create(Prpay prpay) {

		  // 데이터베이스 값 갱신
		  Prpay newPrpay = this.prpayRepository.save(prpay);

		  return newPrpay;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public Prpay update(PrpayUpdateDto prpayUpdateDto, Prpay existPrpay) {

		  // 입력값 대입
		  this.modelMapper.map(prpayUpdateDto, existPrpay);

		  // 데이터베이스 값 갱신
		  this.prpayRepository.save(existPrpay);

		  return existPrpay;
	 }

}
