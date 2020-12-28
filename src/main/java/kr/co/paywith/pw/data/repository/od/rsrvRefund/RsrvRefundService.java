package kr.co.paywith.pw.data.repository.od.rsrvRefund;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RsrvRefundService {

	@Autowired
	RsrvRefundRepository rsrvRefundRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public RsrvRefund create(RsrvRefund rsrvRefund) {

		  // 데이터베이스 값 갱신
		  RsrvRefund newRsrvRefund = this.rsrvRefundRepository.save(rsrvRefund);

		  return newRsrvRefund;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public RsrvRefund update(RsrvRefundUpdateDto rsrvRefundUpdateDto, RsrvRefund existRsrvRefund) {

		  // 입력값 대입
		  this.modelMapper.map(rsrvRefundUpdateDto, existRsrvRefund);

		  // 데이터베이스 값 갱신
		  this.rsrvRefundRepository.save(existRsrvRefund);

		  return existRsrvRefund;
	 }

}
