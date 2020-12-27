package kr.co.paywith.pw.data.repository.mbs.notifHist;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NotifHistService {

	@Autowired
	NotifHistRepository notifHistRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public NotifHist create(NotifHist notifHist) {

		  // 데이터베이스 값 갱신
		  NotifHist newNotifHist = this.notifHistRepository.save(notifHist);

		  return newNotifHist;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public NotifHist update(NotifHistUpdateDto notifHistUpdateDto, NotifHist existNotifHist) {

		  // 입력값 대입
		  this.modelMapper.map(notifHistUpdateDto, existNotifHist);

		  // 데이터베이스 값 갱신
		  this.notifHistRepository.save(existNotifHist);

		  return existNotifHist;
	 }

}
