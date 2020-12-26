package kr.co.paywith.pw.data.repository.od.ordrGoodsOptEtc;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrdrGoodsOptEtcService {

	@Autowired
	OrdrGoodsOptEtcRepository ordrGoodsOptEtcRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public OrdrGoodsOptEtc create(OrdrGoodsOptEtc ordrGoodsOptEtc) {

		  // 데이터베이스 값 갱신
		  OrdrGoodsOptEtc newOrdrGoodsOptEtc = this.ordrGoodsOptEtcRepository.save(ordrGoodsOptEtc);

		  return newOrdrGoodsOptEtc;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public OrdrGoodsOptEtc update(OrdrGoodsOptEtcUpdateDto ordrGoodsOptEtcUpdateDto, OrdrGoodsOptEtc existOrdrGoodsOptEtc) {

		  // 입력값 대입
		  this.modelMapper.map(ordrGoodsOptEtcUpdateDto, existOrdrGoodsOptEtc);

		  // 데이터베이스 값 갱신
		  this.ordrGoodsOptEtcRepository.save(existOrdrGoodsOptEtc);

		  return existOrdrGoodsOptEtc;
	 }

}
