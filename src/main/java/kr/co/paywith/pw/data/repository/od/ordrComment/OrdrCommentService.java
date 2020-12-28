package kr.co.paywith.pw.data.repository.od.ordrComment;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrdrCommentService {

	@Autowired
	OrdrCommentRepository ordrCommentRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public OrdrComment create(OrdrComment ordrComment) {

		  // 데이터베이스 값 갱신
		  OrdrComment newOrdrComment = this.ordrCommentRepository.save(ordrComment);

		  return newOrdrComment;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public OrdrComment update(OrdrCommentUpdateDto ordrCommentUpdateDto, OrdrComment existOrdrComment) {

		  // 입력값 대입
		  this.modelMapper.map(ordrCommentUpdateDto, existOrdrComment);

		  // 데이터베이스 값 갱신
		  this.ordrCommentRepository.save(existOrdrComment);

		  return existOrdrComment;
	 }

}
