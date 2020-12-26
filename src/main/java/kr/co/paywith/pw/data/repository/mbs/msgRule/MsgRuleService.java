package kr.co.paywith.pw.data.repository.mbs.msgRule;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MsgRuleService {

	@Autowired
	MsgRuleRepository msgRuleRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public MsgRule create(MsgRule msgRule) {

		  // 데이터베이스 값 갱신
		  MsgRule newMsgRule = this.msgRuleRepository.save(msgRule);

		  return newMsgRule;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public MsgRule update(MsgRuleUpdateDto msgRuleUpdateDto, MsgRule existMsgRule) {

		  // 입력값 대입
		  this.modelMapper.map(msgRuleUpdateDto, existMsgRule);

		  // 데이터베이스 값 갱신
		  this.msgRuleRepository.save(existMsgRule);

		  return existMsgRule;
	 }

}
