package kr.co.paywith.pw.data.repository.mbs.scoreRule;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ScoreRuleService {

	@Autowired
	ScoreRuleRepository scoreRuleRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public ScoreRule create(ScoreRule scoreRule) {

		  // 데이터베이스 값 갱신
		  ScoreRule newScoreRule = this.scoreRuleRepository.save(scoreRule);

		  return newScoreRule;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public ScoreRule update(ScoreRuleUpdateDto scoreRuleUpdateDto, ScoreRule existScoreRule) {

		  // 입력값 대입
		  this.modelMapper.map(scoreRuleUpdateDto, existScoreRule);

		  // 데이터베이스 값 갱신
		  this.scoreRuleRepository.save(existScoreRule);

		  return existScoreRule;
	 }

}
