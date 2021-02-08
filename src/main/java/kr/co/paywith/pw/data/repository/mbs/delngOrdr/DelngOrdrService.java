package kr.co.paywith.pw.data.repository.mbs.delngOrdr;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DelngOrdrService {

	@Autowired
	DelngOrdrRepository delngOrdrRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public DelngOrdr create(DelngOrdr delngOrdr) {

		  // 데이터베이스 값 갱신
		  DelngOrdr newDelngOrdr = this.delngOrdrRepository.save(delngOrdr);

		  return newDelngOrdr;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public DelngOrdr update(DelngOrdrUpdateDto delngOrdrUpdateDto, DelngOrdr existDelngOrdr) {

		  // 입력값 대입
		  this.modelMapper.map(delngOrdrUpdateDto, existDelngOrdr);

		  // 데이터베이스 값 갱신
		  this.delngOrdrRepository.save(existDelngOrdr);

		  return existDelngOrdr;
	 }

}
