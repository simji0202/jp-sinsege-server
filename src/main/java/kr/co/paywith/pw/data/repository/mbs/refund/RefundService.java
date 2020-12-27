package kr.co.paywith.pw.data.repository.mbs.refund;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RefundService {

	@Autowired
	RefundRepository refundRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public Refund create(Refund refund) {

		  // 데이터베이스 값 갱신
		  Refund newRefund = this.refundRepository.save(refund);

		  return newRefund;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public Refund update(RefundUpdateDto refundUpdateDto, Refund existRefund) {

		  // 입력값 대입
		  this.modelMapper.map(refundUpdateDto, existRefund);

		  // 데이터베이스 값 갱신
		  this.refundRepository.save(existRefund);

		  return existRefund;
	 }

}
