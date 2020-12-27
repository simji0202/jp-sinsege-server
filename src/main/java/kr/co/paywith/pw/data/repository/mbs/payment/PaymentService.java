package kr.co.paywith.pw.data.repository.mbs.payment;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PaymentService {

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public Payment create(Payment payment) {

		  // 데이터베이스 값 갱신
		  Payment newPayment = this.paymentRepository.save(payment);

		  return newPayment;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public Payment update(PaymentUpdateDto paymentUpdateDto, Payment existPayment) {

		  // 입력값 대입
		  this.modelMapper.map(paymentUpdateDto, existPayment);

		  // 데이터베이스 값 갱신
		  this.paymentRepository.save(existPayment);

		  return existPayment;
	 }

}
