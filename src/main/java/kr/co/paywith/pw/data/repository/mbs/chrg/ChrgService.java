package kr.co.paywith.pw.data.repository.mbs.chrg;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ChrgService {

	@Autowired
	ChrgRepository chrgRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public Chrg create(Chrg chrg) {

		  // 데이터베이스 값 갱신
		  Chrg newChrg = this.chrgRepository.save(chrg);

		  return newChrg;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public Chrg update(ChrgUpdateDto chrgUpdateDto, Chrg existChrg) {

		  // 입력값 대입
		  this.modelMapper.map(chrgUpdateDto, existChrg);

		  // 데이터베이스 값 갱신
		  this.chrgRepository.save(existChrg);

		  return existChrg;
	 }

}
