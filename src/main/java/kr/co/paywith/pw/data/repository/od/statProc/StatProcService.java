package kr.co.paywith.pw.data.repository.od.statProc;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StatProcService {

	@Autowired
	StatProcRepository statProcRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public StatProc create(StatProc statProc) {

		  // 데이터베이스 값 갱신
		  StatProc newStatProc = this.statProcRepository.save(statProc);

		  return newStatProc;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public StatProc update(StatProcUpdateDto statProcUpdateDto, StatProc existStatProc) {

		  // 입력값 대입
		  this.modelMapper.map(statProcUpdateDto, existStatProc);

		  // 데이터베이스 값 갱신
		  this.statProcRepository.save(existStatProc);

		  return existStatProc;
	 }

}
