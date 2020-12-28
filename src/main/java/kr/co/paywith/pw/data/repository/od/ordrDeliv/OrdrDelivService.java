package kr.co.paywith.pw.data.repository.od.ordrDeliv;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrdrDelivService {

	@Autowired
	OrdrDelivRepository ordrDelivRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public OrdrDeliv create(OrdrDeliv ordrDeliv) {

		  // 데이터베이스 값 갱신
		  OrdrDeliv newOrdrDeliv = this.ordrDelivRepository.save(ordrDeliv);

		  return newOrdrDeliv;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public OrdrDeliv update(OrdrDelivUpdateDto ordrDelivUpdateDto, OrdrDeliv existOrdrDeliv) {

		  // 입력값 대입
		  this.modelMapper.map(ordrDelivUpdateDto, existOrdrDeliv);

		  // 데이터베이스 값 갱신
		  this.ordrDelivRepository.save(existOrdrDeliv);

		  return existOrdrDeliv;
	 }

}
