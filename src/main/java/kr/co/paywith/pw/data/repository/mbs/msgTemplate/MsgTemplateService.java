package kr.co.paywith.pw.data.repository.mbs.msgTemplate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MsgTemplateService {

	@Autowired
	MsgTemplateRepository msgTemplateRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public MsgTemplate create(MsgTemplate msgTemplate) {

		  // 데이터베이스 값 갱신
		  MsgTemplate newMsgTemplate = this.msgTemplateRepository.save(msgTemplate);

		  return newMsgTemplate;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public MsgTemplate update(MsgTemplateUpdateDto msgTemplateUpdateDto, MsgTemplate existMsgTemplate) {

		  // 입력값 대입
		  this.modelMapper.map(msgTemplateUpdateDto, existMsgTemplate);

		  // 데이터베이스 값 갱신
		  this.msgTemplateRepository.save(existMsgTemplate);

		  return existMsgTemplate;
	 }

}
