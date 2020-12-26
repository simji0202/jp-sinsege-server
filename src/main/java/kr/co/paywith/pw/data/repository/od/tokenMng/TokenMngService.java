package kr.co.paywith.pw.data.repository.od.tokenMng;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TokenMngService {

	@Autowired
	TokenMngRepository tokenMngRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public TokenMng create(TokenMng tokenMng) {

		  // 데이터베이스 값 갱신
		  TokenMng newTokenMng = this.tokenMngRepository.save(tokenMng);

		  return newTokenMng;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public TokenMng update(TokenMngUpdateDto tokenMngUpdateDto, TokenMng existTokenMng) {

		  // 입력값 대입
		  this.modelMapper.map(tokenMngUpdateDto, existTokenMng);

		  // 데이터베이스 값 갱신
		  this.tokenMngRepository.save(existTokenMng);

		  return existTokenMng;
	 }

}
