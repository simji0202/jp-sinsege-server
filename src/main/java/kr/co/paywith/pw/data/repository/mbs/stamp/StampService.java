package kr.co.paywith.pw.data.repository.mbs.stamp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StampService {

	@Autowired
	StampRepository stampRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public Stamp create(Stamp stamp) {

		  // 데이터베이스 값 갱신
		  Stamp newStamp = this.stampRepository.save(stamp);

		  return newStamp;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public Stamp update(StampUpdateDto stampUpdateDto, Stamp existStamp) {

		  // 입력값 대입
		  this.modelMapper.map(stampUpdateDto, existStamp);

		  // 데이터베이스 값 갱신
		  this.stampRepository.save(existStamp);

		  return existStamp;
	 }

}
