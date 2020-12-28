package kr.co.paywith.pw.data.repository.od.ordrPay;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrdrPayService {

	@Autowired
	OrdrPayRepository ordrPayRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public OrdrPay create(OrdrPay ordrPay) {

		  // 데이터베이스 값 갱신
		  OrdrPay newOrdrPay = this.ordrPayRepository.save(ordrPay);

		  return newOrdrPay;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public OrdrPay update(OrdrPayUpdateDto ordrPayUpdateDto, OrdrPay existOrdrPay) {

		  // 입력값 대입
		  this.modelMapper.map(ordrPayUpdateDto, existOrdrPay);

		  // 데이터베이스 값 갱신
		  this.ordrPayRepository.save(existOrdrPay);

		  return existOrdrPay;
	 }

}
