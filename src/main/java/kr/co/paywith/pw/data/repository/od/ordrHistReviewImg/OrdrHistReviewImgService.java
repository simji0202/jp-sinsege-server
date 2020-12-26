package kr.co.paywith.pw.data.repository.od.ordrHistReviewImg;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrdrHistReviewImgService {

	@Autowired
	OrdrHistReviewImgRepository ordrHistReviewImgRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public OrdrHistReviewImg create(OrdrHistReviewImg ordrHistReviewImg) {

		  // 데이터베이스 값 갱신
		  OrdrHistReviewImg newOrdrHistReviewImg = this.ordrHistReviewImgRepository.save(ordrHistReviewImg);

		  return newOrdrHistReviewImg;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public OrdrHistReviewImg update(OrdrHistReviewImgUpdateDto ordrHistReviewImgUpdateDto, OrdrHistReviewImg existOrdrHistReviewImg) {

		  // 입력값 대입
		  this.modelMapper.map(ordrHistReviewImgUpdateDto, existOrdrHistReviewImg);

		  // 데이터베이스 값 갱신
		  this.ordrHistReviewImgRepository.save(existOrdrHistReviewImg);

		  return existOrdrHistReviewImg;
	 }

}
