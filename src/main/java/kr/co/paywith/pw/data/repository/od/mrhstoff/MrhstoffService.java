package kr.co.paywith.pw.data.repository.od.mrhstoff;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MrhstoffService {

	@Autowired
	MrhstoffRepository mrhstoffRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public Mrhstoff create(Mrhstoff mrhstoff) {

		  // 데이터베이스 값 갱신
		  Mrhstoff newMrhstoff = this.mrhstoffRepository.save(mrhstoff);

		  return newMrhstoff;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public Mrhstoff update(MrhstoffUpdateDto mrhstoffUpdateDto, Mrhstoff existMrhstoff) {

		  // 입력값 대입
		  this.modelMapper.map(mrhstoffUpdateDto, existMrhstoff);

		  // 데이터베이스 값 갱신
		  this.mrhstoffRepository.save(existMrhstoff);

		  return existMrhstoff;
	 }

}
