package kr.co.paywith.pw.data.repository.od.ordrHistReview;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrdrHistReviewService {

	@Autowired
	OrdrHistReviewRepository ordrHistReviewRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public OrdrHistReview create(OrdrHistReview ordrHistReview) {

		  // 데이터베이스 값 갱신
		  OrdrHistReview newOrdrHistReview = this.ordrHistReviewRepository.save(ordrHistReview);

		  return newOrdrHistReview;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public OrdrHistReview update(OrdrHistReviewUpdateDto ordrHistReviewUpdateDto, OrdrHistReview existOrdrHistReview) {

		  // 입력값 대입
		  this.modelMapper.map(ordrHistReviewUpdateDto, existOrdrHistReview);

		  // 데이터베이스 값 갱신
		  this.ordrHistReviewRepository.save(existOrdrHistReview);

		  return existOrdrHistReview;
	 }

}
