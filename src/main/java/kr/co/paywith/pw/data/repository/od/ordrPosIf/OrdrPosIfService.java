package kr.co.paywith.pw.data.repository.od.ordrPosIf;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrdrPosIfService {

	@Autowired
	OrdrPosIfRepository ordrPosIfRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public OrdrPosIf create(OrdrPosIf ordrPosIf) {

		  // 데이터베이스 값 갱신
		  OrdrPosIf newOrdrPosIf = this.ordrPosIfRepository.save(ordrPosIf);

		  return newOrdrPosIf;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public OrdrPosIf update(OrdrPosIfUpdateDto ordrPosIfUpdateDto, OrdrPosIf existOrdrPosIf) {

		  // 입력값 대입
		  this.modelMapper.map(ordrPosIfUpdateDto, existOrdrPosIf);

		  // 데이터베이스 값 갱신
		  this.ordrPosIfRepository.save(existOrdrPosIf);

		  return existOrdrPosIf;
	 }

}
