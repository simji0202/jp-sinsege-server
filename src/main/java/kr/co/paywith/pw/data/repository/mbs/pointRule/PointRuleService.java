package kr.co.paywith.pw.data.repository.mbs.pointRule;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PointRuleService {

	@Autowired
	PointRuleRepository pointRuleRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public PointRule create(PointRule pointRule) {

		  // 데이터베이스 값 갱신
		  PointRule newPointRule = this.pointRuleRepository.save(pointRule);

		  return newPointRule;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public PointRule update(PointRuleUpdateDto pointRuleUpdateDto, PointRule existPointRule) {

		  // 입력값 대입
		  this.modelMapper.map(pointRuleUpdateDto, existPointRule);

		  // 데이터베이스 값 갱신
		  this.pointRuleRepository.save(existPointRule);

		  return existPointRule;
	 }

}
