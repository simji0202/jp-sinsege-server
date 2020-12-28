package kr.co.paywith.pw.data.repository.od.userGoodsOptEtc;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserGoodsOptEtcService {

	@Autowired
	UserGoodsOptEtcRepository userGoodsOptEtcRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public UserGoodsOptEtc create(UserGoodsOptEtc userGoodsOptEtc) {

		  // 데이터베이스 값 갱신
		  UserGoodsOptEtc newUserGoodsOptEtc = this.userGoodsOptEtcRepository.save(userGoodsOptEtc);

		  return newUserGoodsOptEtc;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public UserGoodsOptEtc update(UserGoodsOptEtcUpdateDto userGoodsOptEtcUpdateDto, UserGoodsOptEtc existUserGoodsOptEtc) {

		  // 입력값 대입
		  this.modelMapper.map(userGoodsOptEtcUpdateDto, existUserGoodsOptEtc);

		  // 데이터베이스 값 갱신
		  this.userGoodsOptEtcRepository.save(existUserGoodsOptEtc);

		  return existUserGoodsOptEtc;
	 }

}
