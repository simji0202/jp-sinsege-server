package kr.co.paywith.pw.data.repository.mbs.pointRsrvRule;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

@Service
public class PointRsrvRuleService {

	@Autowired
	PointRsrvRuleRepository pointRsrvRuleRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public PointRsrvRule create(PointRsrvRule pointRsrvRule) {

		  // 데이터베이스 값 갱신
		  PointRsrvRule newPointRsrvRule = this.pointRsrvRuleRepository.save(pointRsrvRule);

		  return newPointRsrvRule;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public PointRsrvRule update(PointRsrvRuleUpdateDto pointRsrvRuleUpdateDto, PointRsrvRule existPointRsrvRule) {

		  // 입력값 대입
		  this.modelMapper.map(pointRsrvRuleUpdateDto, existPointRsrvRule);

		  // 데이터베이스 값 갱신
		  this.pointRsrvRuleRepository.save(existPointRsrvRule);

		  return existPointRsrvRule;
	 }

}
