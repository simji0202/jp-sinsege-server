package kr.co.paywith.pw.data.repository.od.staff;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StaffService {

	@Autowired
	StaffRepository staffRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public Staff create(Staff staff) {

		  // 데이터베이스 값 갱신
		  Staff newStaff = this.staffRepository.save(staff);

		  return newStaff;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public Staff update(StaffUpdateDto staffUpdateDto, Staff existStaff) {

		  // 입력값 대입
		  this.modelMapper.map(staffUpdateDto, existStaff);

		  // 데이터베이스 값 갱신
		  this.staffRepository.save(existStaff);

		  return existStaff;
	 }

}
