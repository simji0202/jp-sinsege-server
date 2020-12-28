package kr.co.paywith.pw.data.repository.od.goodsOptEtc;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GoodsOptEtcService {

	@Autowired
	GoodsOptEtcRepository goodsOptEtcRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public GoodsOptEtc create(GoodsOptEtc goodsOptEtc) {

		  // 데이터베이스 값 갱신
		  GoodsOptEtc newGoodsOptEtc = this.goodsOptEtcRepository.save(goodsOptEtc);

		  return newGoodsOptEtc;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public GoodsOptEtc update(GoodsOptEtcUpdateDto goodsOptEtcUpdateDto, GoodsOptEtc existGoodsOptEtc) {

		  // 입력값 대입
		  this.modelMapper.map(goodsOptEtcUpdateDto, existGoodsOptEtc);

		  // 데이터베이스 값 갱신
		  this.goodsOptEtcRepository.save(existGoodsOptEtc);

		  return existGoodsOptEtc;
	 }

}
