package kr.co.paywith.pw.data.repository.od.ordrGoodsCpn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrdrGoodsCpnService {

	@Autowired
	OrdrGoodsCpnRepository ordrGoodsCpnRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public OrdrGoodsCpn create(OrdrGoodsCpn ordrGoodsCpn) {

		  // 데이터베이스 값 갱신
		  OrdrGoodsCpn newOrdrGoodsCpn = this.ordrGoodsCpnRepository.save(ordrGoodsCpn);

		  return newOrdrGoodsCpn;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public OrdrGoodsCpn update(OrdrGoodsCpnUpdateDto ordrGoodsCpnUpdateDto, OrdrGoodsCpn existOrdrGoodsCpn) {

		  // 입력값 대입
		  this.modelMapper.map(ordrGoodsCpnUpdateDto, existOrdrGoodsCpn);

		  // 데이터베이스 값 갱신
		  this.ordrGoodsCpnRepository.save(existOrdrGoodsCpn);

		  return existOrdrGoodsCpn;
	 }

}
