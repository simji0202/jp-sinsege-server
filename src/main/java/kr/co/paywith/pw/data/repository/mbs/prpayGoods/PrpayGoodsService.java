package kr.co.paywith.pw.data.repository.mbs.prpayGoods;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PrpayGoodsService {

	@Autowired
	PrpayGoodsRepository prpayGoodsRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public PrpayGoods create(PrpayGoods prpayGoods) {

		  // 데이터베이스 값 갱신
		  PrpayGoods newPrpayGoods = this.prpayGoodsRepository.save(prpayGoods);

		  return newPrpayGoods;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public PrpayGoods update(PrpayGoodsUpdateDto prpayGoodsUpdateDto, PrpayGoods existPrpayGoods) {

		  // 입력값 대입
		  this.modelMapper.map(prpayGoodsUpdateDto, existPrpayGoods);

		  // 데이터베이스 값 갱신
		  this.prpayGoodsRepository.save(existPrpayGoods);

		  return existPrpayGoods;
	 }

}
