package kr.co.paywith.pw.data.repository.od.timesale;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TimesaleService {

	@Autowired
	TimesaleRepository timesaleRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public Timesale create(Timesale timesale) {

		  // 데이터베이스 값 갱신
		  Timesale newTimesale = this.timesaleRepository.save(timesale);

		  return newTimesale;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public Timesale update(TimesaleUpdateDto timesaleUpdateDto, Timesale existTimesale) {

		  // 입력값 대입
		  this.modelMapper.map(timesaleUpdateDto, existTimesale);

		  // 데이터베이스 값 갱신
		  this.timesaleRepository.save(existTimesale);

		  return existTimesale;
	 }

}
