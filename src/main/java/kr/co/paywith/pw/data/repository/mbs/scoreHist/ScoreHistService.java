package kr.co.paywith.pw.data.repository.mbs.scoreHist;

import java.time.LocalDateTime;

import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import kr.co.paywith.pw.data.repository.user.user.UserInfoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

@Service
public class ScoreHistService {

  @Autowired
  ScoreHistRepository scoreHistRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  private UserInfoRepository userInfoRepository;

  /**
   * 정보 등록
   */
  @Transactional
  public ScoreHist create(ScoreHist scoreHist) {

    int score = scoreHist.getScoreAmt();
    UserInfo userInfo = scoreHist.getUserInfo();
    userInfo.setScore(userInfo.getScore() + score);
    userInfo.setTotalScore(userInfo.getTotalScore() + score);

    // 데이터베이스 값 갱신
    ScoreHist newScoreHist = this.scoreHistRepository.save(scoreHist);

    return newScoreHist;
  }


  /**
   * 스코어 이력 취소 처리. 이력 취소 후 회원 정보 반영
   */
  @Transactional
  public void delete(ScoreHist scoreHist) {
    // 스코어 이력 취소
    scoreHist.setCancelRegDttm(LocalDateTime.now());

    // 데이터베이스 값 갱신
    this.scoreHistRepository.save(scoreHist);

    int score = scoreHist.getScoreAmt();
    UserInfo userInfo = scoreHist.getUserInfo();
    userInfo.setScore(userInfo.getScore() - score);
    userInfo.setTotalScore(userInfo.getTotalScore() - score);

    userInfoRepository.save(userInfo);
  }

}
