package kr.co.paywith.pw.data.repository.od.goodsOrg;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GoodsOrgService {

	@Autowired
	GoodsOrgRepository goodsOrgRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public GoodsOrg create(GoodsOrg goodsOrg) {

		  // 데이터베이스 값 갱신
		  GoodsOrg newGoodsOrg = this.goodsOrgRepository.save(goodsOrg);

		  return newGoodsOrg;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public GoodsOrg update(GoodsOrgUpdateDto goodsOrgUpdateDto, GoodsOrg existGoodsOrg) {

		  // 입력값 대입
		  this.modelMapper.map(goodsOrgUpdateDto, existGoodsOrg);

		  // 데이터베이스 값 갱신
		  this.goodsOrgRepository.save(existGoodsOrg);

		  return existGoodsOrg;
	 }

}
