package kr.co.paywith.pw.data.repository.od.userGoodsOpt;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserGoodsOptService {

	@Autowired
	UserGoodsOptRepository userGoodsOptRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public UserGoodsOpt create(UserGoodsOpt userGoodsOpt) {

		  // 데이터베이스 값 갱신
		  UserGoodsOpt newUserGoodsOpt = this.userGoodsOptRepository.save(userGoodsOpt);

		  return newUserGoodsOpt;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public UserGoodsOpt update(UserGoodsOptUpdateDto userGoodsOptUpdateDto, UserGoodsOpt existUserGoodsOpt) {

		  // 입력값 대입
		  this.modelMapper.map(userGoodsOptUpdateDto, existUserGoodsOpt);

		  // 데이터베이스 값 갱신
		  this.userGoodsOptRepository.save(existUserGoodsOpt);

		  return existUserGoodsOpt;
	 }

}
