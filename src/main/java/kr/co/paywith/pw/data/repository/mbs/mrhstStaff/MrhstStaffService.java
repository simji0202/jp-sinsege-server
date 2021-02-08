package kr.co.paywith.pw.data.repository.mbs.mrhstStaff;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MrhstStaffService {

	@Autowired
	MrhstStaffRepository mrhstStaffRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public MrhstStaff create(MrhstStaff mrhstStaff) {

		  // 데이터베이스 값 갱신
		  MrhstStaff newMrhstStaff = this.mrhstStaffRepository.save(mrhstStaff);

		  return newMrhstStaff;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public MrhstStaff update(MrhstStaffUpdateDto mrhstStaffUpdateDto, MrhstStaff existMrhstStaff) {

		  // 입력값 대입
		  this.modelMapper.map(mrhstStaffUpdateDto, existMrhstStaff);

		  // 데이터베이스 값 갱신
		  this.mrhstStaffRepository.save(existMrhstStaff);

		  return existMrhstStaff;
	 }

}
