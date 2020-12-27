package kr.co.paywith.pw.data.repository.mbs.pointUseRule;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PointUseRuleService {

	@Autowired
	PointUseRuleRepository pointUseRuleRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public PointUseRule create(PointUseRule pointUseRule) {

		  // 데이터베이스 값 갱신
		  PointUseRule newPointUseRule = this.pointUseRuleRepository.save(pointUseRule);

		  return newPointUseRule;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public PointUseRule update(PointUseRuleUpdateDto pointUseRuleUpdateDto, PointUseRule existPointUseRule) {

		  // 입력값 대입
		  this.modelMapper.map(pointUseRuleUpdateDto, existPointUseRule);

		  // 데이터베이스 값 갱신
		  this.pointUseRuleRepository.save(existPointUseRule);

		  return existPointUseRule;
	 }

}
