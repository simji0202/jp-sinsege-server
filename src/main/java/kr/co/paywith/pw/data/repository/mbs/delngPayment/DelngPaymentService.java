package kr.co.paywith.pw.data.repository.mbs.delngPayment;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

@Service
public class DelngPaymentService {

	@Autowired
	DelngPaymentRepository delngPaymentRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public DelngPayment create(DelngPayment delngPayment) {

		  // 데이터베이스 값 갱신
		  DelngPayment newDelngPayment = this.delngPaymentRepository.save(delngPayment);

		  return newDelngPayment;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public DelngPayment update(DelngPaymentUpdateDto delngPaymentUpdateDto, DelngPayment existDelngPayment) {

		  // 입력값 대입
		  this.modelMapper.map(delngPaymentUpdateDto, existDelngPayment);

		  // 데이터베이스 값 갱신
		  this.delngPaymentRepository.save(existDelngPayment);

		  return existDelngPayment;
	 }

}
