package kr.co.paywith.pw.data.repository.od.ordrGoodsOpt;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrdrGoodsOptService {

	@Autowired
	OrdrGoodsOptRepository ordrGoodsOptRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public OrdrGoodsOpt create(OrdrGoodsOpt ordrGoodsOpt) {

		  // 데이터베이스 값 갱신
		  OrdrGoodsOpt newOrdrGoodsOpt = this.ordrGoodsOptRepository.save(ordrGoodsOpt);

		  return newOrdrGoodsOpt;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public OrdrGoodsOpt update(OrdrGoodsOptUpdateDto ordrGoodsOptUpdateDto, OrdrGoodsOpt existOrdrGoodsOpt) {

		  // 입력값 대입
		  this.modelMapper.map(ordrGoodsOptUpdateDto, existOrdrGoodsOpt);

		  // 데이터베이스 값 갱신
		  this.ordrGoodsOptRepository.save(existOrdrGoodsOpt);

		  return existOrdrGoodsOpt;
	 }

}
