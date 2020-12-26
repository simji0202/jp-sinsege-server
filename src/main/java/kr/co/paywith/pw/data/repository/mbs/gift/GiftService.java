package kr.co.paywith.pw.data.repository.mbs.gift;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GiftService {

	@Autowired
	GiftRepository giftRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public Gift create(Gift gift) {

		  // 데이터베이스 값 갱신
		  Gift newGift = this.giftRepository.save(gift);

		  return newGift;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public Gift update(GiftUpdateDto giftUpdateDto, Gift existGift) {

		  // 입력값 대입
		  this.modelMapper.map(giftUpdateDto, existGift);

		  // 데이터베이스 값 갱신
		  this.giftRepository.save(existGift);

		  return existGift;
	 }

}
