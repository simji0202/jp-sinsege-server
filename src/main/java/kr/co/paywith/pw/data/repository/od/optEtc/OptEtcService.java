package kr.co.paywith.pw.data.repository.od.optEtc;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OptEtcService {

	@Autowired
	OptEtcRepository optEtcRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public OptEtc create(OptEtc optEtc) {

		  // 데이터베이스 값 갱신
		  OptEtc newOptEtc = this.optEtcRepository.save(optEtc);

		  return newOptEtc;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public OptEtc update(OptEtcUpdateDto optEtcUpdateDto, OptEtc existOptEtc) {

		  // 입력값 대입
		  this.modelMapper.map(optEtcUpdateDto, existOptEtc);

		  // 데이터베이스 값 갱신
		  this.optEtcRepository.save(existOptEtc);

		  return existOptEtc;
	 }

}
