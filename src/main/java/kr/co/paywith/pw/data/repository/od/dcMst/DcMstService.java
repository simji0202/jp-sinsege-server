package kr.co.paywith.pw.data.repository.od.dcMst;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DcMstService {

	@Autowired
	DcMstRepository dcMstRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public DcMst create(DcMst dcMst) {

		  // 데이터베이스 값 갱신
		  DcMst newDcMst = this.dcMstRepository.save(dcMst);

		  return newDcMst;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public DcMst update(DcMstUpdateDto dcMstUpdateDto, DcMst existDcMst) {

		  // 입력값 대입
		  this.modelMapper.map(dcMstUpdateDto, existDcMst);

		  // 데이터베이스 값 갱신
		  this.dcMstRepository.save(existDcMst);

		  return existDcMst;
	 }

}
