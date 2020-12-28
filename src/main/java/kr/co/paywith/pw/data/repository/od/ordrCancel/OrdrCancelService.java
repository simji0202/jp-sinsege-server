package kr.co.paywith.pw.data.repository.od.ordrCancel;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrdrCancelService {

	@Autowired
	OrdrCancelRepository ordrCancelRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public OrdrCancel create(OrdrCancel ordrCancel) {

		  // 데이터베이스 값 갱신
		  OrdrCancel newOrdrCancel = this.ordrCancelRepository.save(ordrCancel);

		  return newOrdrCancel;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public OrdrCancel update(OrdrCancelUpdateDto ordrCancelUpdateDto, OrdrCancel existOrdrCancel) {

		  // 입력값 대입
		  this.modelMapper.map(ordrCancelUpdateDto, existOrdrCancel);

		  // 데이터베이스 값 갱신
		  this.ordrCancelRepository.save(existOrdrCancel);

		  return existOrdrCancel;
	 }

}
