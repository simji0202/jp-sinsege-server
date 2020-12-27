package kr.co.paywith.pw.data.repository.mbs.notifMrhst;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NotifMrhstService {

	@Autowired
	NotifMrhstRepository notifMrhstRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public NotifMrhst create(NotifMrhst notifMrhst) {

		  // 데이터베이스 값 갱신
		  NotifMrhst newNotifMrhst = this.notifMrhstRepository.save(notifMrhst);

		  return newNotifMrhst;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public NotifMrhst update(NotifMrhstUpdateDto notifMrhstUpdateDto, NotifMrhst existNotifMrhst) {

		  // 입력값 대입
		  this.modelMapper.map(notifMrhstUpdateDto, existNotifMrhst);

		  // 데이터베이스 값 갱신
		  this.notifMrhstRepository.save(existNotifMrhst);

		  return existNotifMrhst;
	 }

}
