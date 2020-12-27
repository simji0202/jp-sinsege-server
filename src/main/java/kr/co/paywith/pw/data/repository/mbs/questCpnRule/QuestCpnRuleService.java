package kr.co.paywith.pw.data.repository.mbs.questCpnRule;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class QuestCpnRuleService {

	@Autowired
	QuestCpnRuleRepository questCpnRuleRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public QuestCpnRule create(QuestCpnRule questCpnRule) {

		  // 데이터베이스 값 갱신
		  QuestCpnRule newQuestCpnRule = this.questCpnRuleRepository.save(questCpnRule);

		  return newQuestCpnRule;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public QuestCpnRule update(QuestCpnRuleUpdateDto questCpnRuleUpdateDto, QuestCpnRule existQuestCpnRule) {

		  // 입력값 대입
		  this.modelMapper.map(questCpnRuleUpdateDto, existQuestCpnRule);

		  // 데이터베이스 값 갱신
		  this.questCpnRuleRepository.save(existQuestCpnRule);

		  return existQuestCpnRule;
	 }

}
