package kr.co.paywith.pw.data.repository.od.seat;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SeatService {

	@Autowired
	SeatRepository seatRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public Seat create(Seat seat) {

		  // 데이터베이스 값 갱신
		  Seat newSeat = this.seatRepository.save(seat);

		  return newSeat;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public Seat update(SeatUpdateDto seatUpdateDto, Seat existSeat) {

		  // 입력값 대입
		  this.modelMapper.map(seatUpdateDto, existSeat);

		  // 데이터베이스 값 갱신
		  this.seatRepository.save(existSeat);

		  return existSeat;
	 }

}
