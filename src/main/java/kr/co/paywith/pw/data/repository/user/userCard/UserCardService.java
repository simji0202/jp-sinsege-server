package kr.co.paywith.pw.data.repository.user.userCard;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

@Service
public class UserCardService {

	@Autowired
	UserCardRepository userCardRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public UserCard create(UserCard userCard) {

		  // 데이터베이스 값 갱신
		  UserCard newUserCard = this.userCardRepository.save(userCard);

		  return newUserCard;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public UserCard update(UserCardUpdateDto userCardUpdateDto, UserCard existUserCard) {

		  // 입력값 대입
		  this.modelMapper.map(userCardUpdateDto, existUserCard);

		  // 데이터베이스 값 갱신
		  this.userCardRepository.save(existUserCard);

		  return existUserCard;
	 }

}
