package kr.co.paywith.pw.data.repository.mbs.point;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PointService {

	@Autowired
	PointRepository pointRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public Point create(Point point) {

		  // 데이터베이스 값 갱신
		  Point newPoint = this.pointRepository.save(point);

		  return newPoint;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public Point update(PointUpdateDto pointUpdateDto, Point existPoint) {

		  // 입력값 대입
		  this.modelMapper.map(pointUpdateDto, existPoint);

		  // 데이터베이스 값 갱신
		  this.pointRepository.save(existPoint);

		  return existPoint;
	 }

}
