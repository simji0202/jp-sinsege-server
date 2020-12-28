package kr.co.paywith.pw.data.repository.od.ordrCpn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrdrCpnService {

	@Autowired
	OrdrCpnRepository ordrCpnRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public OrdrCpn create(OrdrCpn ordrCpn) {

		  // 데이터베이스 값 갱신
		  OrdrCpn newOrdrCpn = this.ordrCpnRepository.save(ordrCpn);

		  return newOrdrCpn;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public OrdrCpn update(OrdrCpnUpdateDto ordrCpnUpdateDto, OrdrCpn existOrdrCpn) {

		  // 입력값 대입
		  this.modelMapper.map(ordrCpnUpdateDto, existOrdrCpn);

		  // 데이터베이스 값 갱신
		  this.ordrCpnRepository.save(existOrdrCpn);

		  return existOrdrCpn;
	 }

}
