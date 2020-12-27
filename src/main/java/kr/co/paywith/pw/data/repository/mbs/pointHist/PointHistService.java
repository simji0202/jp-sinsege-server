package kr.co.paywith.pw.data.repository.mbs.pointHist;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PointHistService {

	@Autowired
	PointHistRepository pointHistRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public PointHist create(PointHist pointHist) {

		  // 데이터베이스 값 갱신
		  PointHist newPointHist = this.pointHistRepository.save(pointHist);

		  return newPointHist;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public PointHist update(PointHistUpdateDto pointHistUpdateDto, PointHist existPointHist) {

		  // 입력값 대입
		  this.modelMapper.map(pointHistUpdateDto, existPointHist);

		  // 데이터베이스 값 갱신
		  this.pointHistRepository.save(existPointHist);

		  return existPointHist;
	 }

}
