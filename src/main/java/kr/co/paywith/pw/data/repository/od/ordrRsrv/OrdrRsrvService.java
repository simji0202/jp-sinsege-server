package kr.co.paywith.pw.data.repository.od.ordrRsrv;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrdrRsrvService {

	@Autowired
	OrdrRsrvRepository ordrRsrvRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public OrdrRsrv create(OrdrRsrv ordrRsrv) {

		  // 데이터베이스 값 갱신
		  OrdrRsrv newOrdrRsrv = this.ordrRsrvRepository.save(ordrRsrv);

		  return newOrdrRsrv;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public OrdrRsrv update(OrdrRsrvUpdateDto ordrRsrvUpdateDto, OrdrRsrv existOrdrRsrv) {

		  // 입력값 대입
		  this.modelMapper.map(ordrRsrvUpdateDto, existOrdrRsrv);

		  // 데이터베이스 값 갱신
		  this.ordrRsrvRepository.save(existOrdrRsrv);

		  return existOrdrRsrv;
	 }

}
