package kr.co.paywith.pw.data.repository.mbs.scoreHist;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ScoreHistService {

	@Autowired
	ScoreHistRepository scoreHistRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public ScoreHist create(ScoreHist scoreHist) {

		  // 데이터베이스 값 갱신
		  ScoreHist newScoreHist = this.scoreHistRepository.save(scoreHist);

		  return newScoreHist;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public ScoreHist update(ScoreHistUpdateDto scoreHistUpdateDto, ScoreHist existScoreHist) {

		  // 입력값 대입
		  this.modelMapper.map(scoreHistUpdateDto, existScoreHist);

		  // 데이터베이스 값 갱신
		  this.scoreHistRepository.save(existScoreHist);

		  return existScoreHist;
	 }

}
