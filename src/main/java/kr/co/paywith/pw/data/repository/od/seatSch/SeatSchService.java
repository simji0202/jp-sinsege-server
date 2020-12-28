package kr.co.paywith.pw.data.repository.od.seatSch;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SeatSchService {

	@Autowired
	SeatSchRepository seatSchRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public SeatSch create(SeatSch seatSch) {

		  // 데이터베이스 값 갱신
		  SeatSch newSeatSch = this.seatSchRepository.save(seatSch);

		  return newSeatSch;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public SeatSch update(SeatSchUpdateDto seatSchUpdateDto, SeatSch existSeatSch) {

		  // 입력값 대입
		  this.modelMapper.map(seatSchUpdateDto, existSeatSch);

		  // 데이터베이스 값 갱신
		  this.seatSchRepository.save(existSeatSch);

		  return existSeatSch;
	 }

}
