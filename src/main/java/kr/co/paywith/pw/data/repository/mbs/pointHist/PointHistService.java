package kr.co.paywith.pw.data.repository.mbs.pointHist;

import java.time.ZonedDateTime;
import kr.co.paywith.pw.data.repository.mbs.pointHist.PointHist;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import kr.co.paywith.pw.data.repository.user.user.UserInfoRepository;
import kr.co.paywith.pw.data.repository.user.userCard.UserCard;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PointHistService {

  @Autowired
  PointHistRepository pointHistRepository;

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
  public PointHist create(PointHist pointHist) {

    int point = pointHist.getPointAmt();
    UserCard userCard = pointHist.getUserInfo().getUserCard();
    userCard.setPointAmt(userCard.getPointAmt() + point);
    switch (pointHist.getPointHistType()) {
      case RSRV:
        userCard.setPointTotalAmt(userCard.getPointTotalAmt() + point);
        break;
    }

    // 데이터베이스 값 갱신
    PointHist newPointHist = this.pointHistRepository.save(pointHist);

    return newPointHist;
  }

  /**
   * 포인트 이력 취소 처리. 이력 취소 후 회원 카드 정보 반영
   */
  @Transactional
  public void delete(PointHist pointHist) {
    // 스코어 이력 취소
    pointHist.setCancelRegDttm(ZonedDateTime.now());

    // 데이터베이스 값 갱신
    this.pointHistRepository.save(pointHist);

    int point = pointHist.getPointAmt();
    UserCard userCard = pointHist.getUserInfo().getUserCard();
    userCard.setPointAmt(userCard.getPointAmt() - point);

    switch (pointHist.getPointHistType()) {
      case RSRV:
        userCard.setPointTotalAmt(userCard.getPointTotalAmt() - point);
        break;
    }

    userInfoRepository.save(pointHist.getUserInfo());
  }
}
