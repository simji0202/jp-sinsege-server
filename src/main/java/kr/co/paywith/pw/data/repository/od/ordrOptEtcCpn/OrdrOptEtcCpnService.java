package kr.co.paywith.pw.data.repository.od.ordrOptEtcCpn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrdrOptEtcCpnService {

	@Autowired
	OrdrOptEtcCpnRepository ordrOptEtcCpnRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public OrdrOptEtcCpn create(OrdrOptEtcCpn ordrOptEtcCpn) {

		  // 데이터베이스 값 갱신
		  OrdrOptEtcCpn newOrdrOptEtcCpn = this.ordrOptEtcCpnRepository.save(ordrOptEtcCpn);

		  return newOrdrOptEtcCpn;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public OrdrOptEtcCpn update(OrdrOptEtcCpnUpdateDto ordrOptEtcCpnUpdateDto, OrdrOptEtcCpn existOrdrOptEtcCpn) {

		  // 입력값 대입
		  this.modelMapper.map(ordrOptEtcCpnUpdateDto, existOrdrOptEtcCpn);

		  // 데이터베이스 값 갱신
		  this.ordrOptEtcCpnRepository.save(existOrdrOptEtcCpn);

		  return existOrdrOptEtcCpn;
	 }

}