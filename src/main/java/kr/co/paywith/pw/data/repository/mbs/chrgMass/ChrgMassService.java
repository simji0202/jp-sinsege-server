package kr.co.paywith.pw.data.repository.mbs.chrgMass;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ChrgMassService {

	@Autowired
	ChrgMassRepository chrgMassRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public ChrgMass create(ChrgMass chrgMass) {

		  // 데이터베이스 값 갱신
		  ChrgMass newChrgMass = this.chrgMassRepository.save(chrgMass);

		  return newChrgMass;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public ChrgMass update(ChrgMassUpdateDto chrgMassUpdateDto, ChrgMass existChrgMass) {

		  // 입력값 대입
		  this.modelMapper.map(chrgMassUpdateDto, existChrgMass);

		  // 데이터베이스 값 갱신
		  this.chrgMassRepository.save(existChrgMass);

		  return existChrgMass;
	 }

}
