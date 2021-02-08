package kr.co.paywith.pw.data.repository.mbs.mrhstOrdr;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MrhstOrdrService {

	@Autowired
	MrhstOrdrRepository mrhstOrdrRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public MrhstOrdr create(MrhstOrdr mrhstOrdr) {

		  // 데이터베이스 값 갱신
		  MrhstOrdr newMrhstOrdr = this.mrhstOrdrRepository.save(mrhstOrdr);

		  return newMrhstOrdr;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public MrhstOrdr update(MrhstOrdrUpdateDto mrhstOrdrUpdateDto, MrhstOrdr existMrhstOrdr) {

		  // 입력값 대입
		  this.modelMapper.map(mrhstOrdrUpdateDto, existMrhstOrdr);

		  // 데이터베이스 값 갱신
		  this.mrhstOrdrRepository.save(existMrhstOrdr);

		  return existMrhstOrdr;
	 }

}
