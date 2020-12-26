package kr.co.paywith.pw.data.repository.od.seatUse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SeatUseService {

	@Autowired
	SeatUseRepository seatUseRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public SeatUse create(SeatUse seatUse) {

		  // 데이터베이스 값 갱신
		  SeatUse newSeatUse = this.seatUseRepository.save(seatUse);

		  return newSeatUse;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public SeatUse update(SeatUseUpdateDto seatUseUpdateDto, SeatUse existSeatUse) {

		  // 입력값 대입
		  this.modelMapper.map(seatUseUpdateDto, existSeatUse);

		  // 데이터베이스 값 갱신
		  this.seatUseRepository.save(existSeatUse);

		  return existSeatUse;
	 }

}
