package kr.co.paywith.pw.data.repository.od.exceptMst;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ExceptMstService {

	@Autowired
	ExceptMstRepository exceptMstRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public ExceptMst create(ExceptMst exceptMst) {

		  // 데이터베이스 값 갱신
		  ExceptMst newExceptMst = this.exceptMstRepository.save(exceptMst);

		  return newExceptMst;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public ExceptMst update(ExceptMstUpdateDto exceptMstUpdateDto, ExceptMst existExceptMst) {

		  // 입력값 대입
		  this.modelMapper.map(exceptMstUpdateDto, existExceptMst);

		  // 데이터베이스 값 갱신
		  this.exceptMstRepository.save(existExceptMst);

		  return existExceptMst;
	 }

}
