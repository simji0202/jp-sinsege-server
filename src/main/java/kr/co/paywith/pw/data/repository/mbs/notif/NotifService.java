package kr.co.paywith.pw.data.repository.mbs.notif;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NotifService {

	@Autowired
	NotifRepository notifRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public Notif create(Notif notif) {

		  // 데이터베이스 값 갱신
		  Notif newNotif = this.notifRepository.save(notif);

		  return newNotif;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public Notif update(NotifUpdateDto notifUpdateDto, Notif existNotif) {

		  // 입력값 대입
		  this.modelMapper.map(notifUpdateDto, existNotif);

		  // 데이터베이스 값 갱신
		  this.notifRepository.save(existNotif);

		  return existNotif;
	 }

}
