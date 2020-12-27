package kr.co.paywith.pw.data.repository.mbs.stat;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StatService {

	@Autowired
	StatRepository statRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public Stat create(Stat stat) {

		  // 데이터베이스 값 갱신
		  Stat newStat = this.statRepository.save(stat);

		  return newStat;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public Stat update(StatUpdateDto statUpdateDto, Stat existStat) {

		  // 입력값 대입
		  this.modelMapper.map(statUpdateDto, existStat);

		  // 데이터베이스 값 갱신
		  this.statRepository.save(existStat);

		  return existStat;
	 }

}
