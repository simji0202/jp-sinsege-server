package kr.co.paywith.pw.data.repository.mbs.delngOrdrSeatTimetable;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DelngOrdrSeatTimetableService {

	@Autowired
	DelngOrdrSeatTimetableRepository delngOrdrSeatTimetableRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public DelngOrdrSeatTimetable create(DelngOrdrSeatTimetable delngOrdrSeatTimetable) {

		  // 데이터베이스 값 갱신
		  DelngOrdrSeatTimetable newDelngOrdrSeatTimetable = this.delngOrdrSeatTimetableRepository.save(delngOrdrSeatTimetable);

		  return newDelngOrdrSeatTimetable;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public DelngOrdrSeatTimetable update(DelngOrdrSeatTimetableUpdateDto delngOrdrSeatTimetableUpdateDto, DelngOrdrSeatTimetable existDelngOrdrSeatTimetable) {

		  // 입력값 대입
		  this.modelMapper.map(delngOrdrSeatTimetableUpdateDto, existDelngOrdrSeatTimetable);

		  // 데이터베이스 값 갱신
		  this.delngOrdrSeatTimetableRepository.save(existDelngOrdrSeatTimetable);

		  return existDelngOrdrSeatTimetable;
	 }

}
