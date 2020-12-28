package kr.co.paywith.pw.data.repository.user.userStamp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

@Service
public class UserStampService {

	@Autowired
	UserStampRepository userStampRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public UserStamp create(UserStamp userStamp) {

		  // 데이터베이스 값 갱신
		  UserStamp newUserStamp = this.userStampRepository.save(userStamp);

		  return newUserStamp;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public UserStamp update(UserStampUpdateDto userStampUpdateDto, UserStamp existUserStamp) {

		  // 입력값 대입
		  this.modelMapper.map(userStampUpdateDto, existUserStamp);

		  // 데이터베이스 값 갱신
		  this.userStampRepository.save(existUserStamp);

		  return existUserStamp;
	 }

}
