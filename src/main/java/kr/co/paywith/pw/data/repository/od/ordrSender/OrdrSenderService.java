package kr.co.paywith.pw.data.repository.od.ordrSender;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrdrSenderService {

	@Autowired
	OrdrSenderRepository ordrSenderRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public OrdrSender create(OrdrSender ordrSender) {

		  // 데이터베이스 값 갱신
		  OrdrSender newOrdrSender = this.ordrSenderRepository.save(ordrSender);

		  return newOrdrSender;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public OrdrSender update(OrdrSenderUpdateDto ordrSenderUpdateDto, OrdrSender existOrdrSender) {

		  // 입력값 대입
		  this.modelMapper.map(ordrSenderUpdateDto, existOrdrSender);

		  // 데이터베이스 값 갱신
		  this.ordrSenderRepository.save(existOrdrSender);

		  return existOrdrSender;
	 }

}
