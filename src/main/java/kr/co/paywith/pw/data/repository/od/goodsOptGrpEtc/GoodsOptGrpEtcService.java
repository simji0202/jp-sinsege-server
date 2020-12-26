package kr.co.paywith.pw.data.repository.od.goodsOptGrpEtc;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GoodsOptGrpEtcService {

	@Autowired
	GoodsOptGrpEtcRepository goodsOptGrpEtcRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public GoodsOptGrpEtc create(GoodsOptGrpEtc goodsOptGrpEtc) {

		  // 데이터베이스 값 갱신
		  GoodsOptGrpEtc newGoodsOptGrpEtc = this.goodsOptGrpEtcRepository.save(goodsOptGrpEtc);

		  return newGoodsOptGrpEtc;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public GoodsOptGrpEtc update(GoodsOptGrpEtcUpdateDto goodsOptGrpEtcUpdateDto, GoodsOptGrpEtc existGoodsOptGrpEtc) {

		  // 입력값 대입
		  this.modelMapper.map(goodsOptGrpEtcUpdateDto, existGoodsOptGrpEtc);

		  // 데이터베이스 값 갱신
		  this.goodsOptGrpEtcRepository.save(existGoodsOptGrpEtc);

		  return existGoodsOptGrpEtc;
	 }

}
