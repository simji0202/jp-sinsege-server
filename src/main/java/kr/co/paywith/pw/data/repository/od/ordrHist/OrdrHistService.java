package kr.co.paywith.pw.data.repository.od.ordrHist;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrdrHistService {

	@Autowired
	OrdrHistRepository ordrHistRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public OrdrHist create(OrdrHist ordrHist) {

		  // 데이터베이스 값 갱신
		  OrdrHist newOrdrHist = this.ordrHistRepository.save(ordrHist);

		  return newOrdrHist;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public OrdrHist update(OrdrHistUpdateDto ordrHistUpdateDto, OrdrHist existOrdrHist) {

		  // 입력값 대입
		  this.modelMapper.map(ordrHistUpdateDto, existOrdrHist);

		  // 데이터베이스 값 갱신
		  this.ordrHistRepository.save(existOrdrHist);

		  return existOrdrHist;
	 }

}
