package kr.co.paywith.pw.data.repository.od.goodsOpt;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GoodsOptService {

	@Autowired
	GoodsOptRepository goodsOptRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public GoodsOpt create(GoodsOpt goodsOpt) {

		  // 데이터베이스 값 갱신
		  GoodsOpt newGoodsOpt = this.goodsOptRepository.save(goodsOpt);

		  return newGoodsOpt;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public GoodsOpt update(GoodsOptUpdateDto goodsOptUpdateDto, GoodsOpt existGoodsOpt) {

		  // 입력값 대입
		  this.modelMapper.map(goodsOptUpdateDto, existGoodsOpt);

		  // 데이터베이스 값 갱신
		  this.goodsOptRepository.save(existGoodsOpt);

		  return existGoodsOpt;
	 }

}
