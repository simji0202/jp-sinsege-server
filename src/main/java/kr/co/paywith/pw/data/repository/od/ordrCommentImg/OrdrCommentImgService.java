package kr.co.paywith.pw.data.repository.od.ordrCommentImg;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrdrCommentImgService {

	@Autowired
	OrdrCommentImgRepository ordrCommentImgRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public OrdrCommentImg create(OrdrCommentImg ordrCommentImg) {

		  // 데이터베이스 값 갱신
		  OrdrCommentImg newOrdrCommentImg = this.ordrCommentImgRepository.save(ordrCommentImg);

		  return newOrdrCommentImg;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public OrdrCommentImg update(OrdrCommentImgUpdateDto ordrCommentImgUpdateDto, OrdrCommentImg existOrdrCommentImg) {

		  // 입력값 대입
		  this.modelMapper.map(ordrCommentImgUpdateDto, existOrdrCommentImg);

		  // 데이터베이스 값 갱신
		  this.ordrCommentImgRepository.save(existOrdrCommentImg);

		  return existOrdrCommentImg;
	 }

}
