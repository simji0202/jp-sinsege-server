package kr.co.paywith.pw.data.repository.od.userMrhst;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserMrhstService {

	@Autowired
	UserMrhstRepository userMrhstRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public UserMrhst create(UserMrhst userMrhst) {

		  // 데이터베이스 값 갱신
		  UserMrhst newUserMrhst = this.userMrhstRepository.save(userMrhst);

		  return newUserMrhst;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public UserMrhst update(UserMrhstUpdateDto userMrhstUpdateDto, UserMrhst existUserMrhst) {

		  // 입력값 대입
		  this.modelMapper.map(userMrhstUpdateDto, existUserMrhst);

		  // 데이터베이스 값 갱신
		  this.userMrhstRepository.save(existUserMrhst);

		  return existUserMrhst;
	 }

}
