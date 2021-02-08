package kr.co.paywith.pw.data.repository.mbs.delngReview;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DelngReviewService {

	@Autowired
	DelngReviewRepository delngReviewRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public DelngReview create(DelngReview delngReview) {

		  // 데이터베이스 값 갱신
		  DelngReview newDelngReview = this.delngReviewRepository.save(delngReview);

		  return newDelngReview;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public DelngReview update(DelngReviewUpdateDto delngReviewUpdateDto, DelngReview existDelngReview) {

		  // 입력값 대입
		  this.modelMapper.map(delngReviewUpdateDto, existDelngReview);

		  // 데이터베이스 값 갱신
		  this.delngReviewRepository.save(existDelngReview);

		  return existDelngReview;
	 }

}
