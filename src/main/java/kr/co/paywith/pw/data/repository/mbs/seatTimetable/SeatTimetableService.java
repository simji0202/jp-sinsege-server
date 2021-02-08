package kr.co.paywith.pw.data.repository.mbs.seatTimetable;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SeatTimetableService {

	@Autowired
	SeatTimetableRepository seatTimetableRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public SeatTimetable create(SeatTimetable seatTimetable) {

		  // 데이터베이스 값 갱신
		  SeatTimetable newSeatTimetable = this.seatTimetableRepository.save(seatTimetable);

		  return newSeatTimetable;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public SeatTimetable update(SeatTimetableUpdateDto seatTimetableUpdateDto, SeatTimetable existSeatTimetable) {

		  // 입력값 대입
		  this.modelMapper.map(seatTimetableUpdateDto, existSeatTimetable);

		  // 데이터베이스 값 갱신
		  this.seatTimetableRepository.save(existSeatTimetable);

		  return existSeatTimetable;
	 }

}
