package kr.co.paywith.pw.data.repository.od.userAddr;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserAddrService {

	@Autowired
	UserAddrRepository userAddrRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public UserAddr create(UserAddr userAddr) {

		  // 데이터베이스 값 갱신
		  UserAddr newUserAddr = this.userAddrRepository.save(userAddr);

		  return newUserAddr;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public UserAddr update(UserAddrUpdateDto userAddrUpdateDto, UserAddr existUserAddr) {

		  // 입력값 대입
		  this.modelMapper.map(userAddrUpdateDto, existUserAddr);

		  // 데이터베이스 값 갱신
		  this.userAddrRepository.save(existUserAddr);

		  return existUserAddr;
	 }

}
