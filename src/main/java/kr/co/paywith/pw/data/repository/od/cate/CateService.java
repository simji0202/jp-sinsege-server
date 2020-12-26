package kr.co.paywith.pw.data.repository.od.cate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CateService {

	@Autowired
	CateRepository cateRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public Cate create(Cate cate) {

		  // 데이터베이스 값 갱신
		  Cate newCate = this.cateRepository.save(cate);

		  return newCate;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public Cate update(CateUpdateDto cateUpdateDto, Cate existCate) {

		  // 입력값 대입
		  this.modelMapper.map(cateUpdateDto, existCate);

		  // 데이터베이스 값 갱신
		  this.cateRepository.save(existCate);

		  return existCate;
	 }

}
