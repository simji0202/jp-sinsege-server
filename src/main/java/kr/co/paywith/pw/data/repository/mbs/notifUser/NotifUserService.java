package kr.co.paywith.pw.data.repository.mbs.notifUser;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NotifUserService {

	@Autowired
	NotifUserRepository notifUserRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public NotifUser create(NotifUser notifUser) {

		  // 데이터베이스 값 갱신
		  NotifUser newNotifUser = this.notifUserRepository.save(notifUser);

		  return newNotifUser;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public NotifUser update(NotifUserUpdateDto notifUserUpdateDto, NotifUser existNotifUser) {

		  // 입력값 대입
		  this.modelMapper.map(notifUserUpdateDto, existNotifUser);

		  // 데이터베이스 값 갱신
		  this.notifUserRepository.save(existNotifUser);

		  return existNotifUser;
	 }

}
