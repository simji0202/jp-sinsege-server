package kr.co.paywith.pw.data.repository.mbs.brandPg;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BrandPgService {

	@Autowired
	BrandPgRepository brandPgRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public BrandPg create(BrandPg brandPg) {

		  // 데이터베이스 값 갱신
		  BrandPg newBrandPg = this.brandPgRepository.save(brandPg);

		  return newBrandPg;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public BrandPg update(BrandPgUpdateDto brandPgUpdateDto, BrandPg existBrandPg) {

		  // 입력값 대입
		  this.modelMapper.map(brandPgUpdateDto, existBrandPg);

		  // 데이터베이스 값 갱신
		  this.brandPgRepository.save(existBrandPg);

		  return existBrandPg;
	 }

}
