package kr.co.paywith.pw.data.repository.od.cpnMst;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CpnMstService {

	@Autowired
	CpnMstRepository cpnMstRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public CpnMst create(CpnMst cpnMst) {

		  // 데이터베이스 값 갱신
		  CpnMst newCpnMst = this.cpnMstRepository.save(cpnMst);

		  return newCpnMst;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public CpnMst update(CpnMstUpdateDto cpnMstUpdateDto, CpnMst existCpnMst) {

		  // 입력값 대입
		  this.modelMapper.map(cpnMstUpdateDto, existCpnMst);

		  // 데이터베이스 값 갱신
		  this.cpnMstRepository.save(existCpnMst);

		  return existCpnMst;
	 }

}
