package kr.co.paywith.pw.data.repository.mbs.stampHist;

import com.querydsl.core.BooleanBuilder;
import java.time.ZonedDateTime;
import kr.co.paywith.pw.data.repository.enumeration.CpnIssuRuleCd;
import kr.co.paywith.pw.data.repository.enumeration.CpnSttsCd;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnRepository;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnService;
import kr.co.paywith.pw.data.repository.mbs.cpn.QCpn;
import kr.co.paywith.pw.data.repository.mbs.cpnIssu.CpnIssuService;
import kr.co.paywith.pw.data.repository.mbs.stamp.Stamp;
import kr.co.paywith.pw.data.repository.mbs.stamp.StampRepository;
import kr.co.paywith.pw.data.repository.mbs.stamp.StampService;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import kr.co.paywith.pw.data.repository.user.user.UserInfoRepository;
import kr.co.paywith.pw.data.repository.user.userCard.UserCard;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

@Service
public class StampHistService {

  @Autowired
  private StampHistRepository stampHistRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  private CpnRepository cpnRepository;

  @Autowired
  private CpnService cpnService;

  @Autowired
  private CpnIssuService cpnIssuService;

  @Autowired
  private StampService stampService;

  @Autowired
  private StampRepository stampRepository;

  @Autowired
  private UserInfoRepository userInfoRepository;

  /**
   * 정보 등록
   */
  @Transactional
  public StampHist create(StampHist stampHist) {

    // 데이터베이스 값 갱신
    StampHist newStampHist = this.stampHistRepository.save(stampHist);
    // kms: TODO 멤버십 구조 개편 후 등급 저장

    switch (newStampHist.getStampHistTypeCd()) {
      case RSRV:
      case D_RSRV:// 적립이면 새롭게 Stamp 생성
        for (int i = 0; i < stampHist.getCnt(); i++) {
          Stamp stamp = new Stamp();
          stamp.setStampHist(newStampHist);
          // kms: TODO 스탬프 만료일 설정
//            stamp.setExpiredDttm();
          stampService.create(stamp);
        }
        break;

      case CPN: // 정책으로 정한 쿠폰 발급에 필요한 스탬프개수 만큼 stamp 변경
        for (Cpn cpn : stampHist.getCpnIssu().getCpnList()) {
          // kms: TODO 브랜드 정책별 쿠폰 발급에 필요한 개수만큼 Stamp 상태 CPN 변경
        }
        break;

      case USE: // 정책으로 정한 사용에 필요한 스탬프 개수 만큼 stamp 변경
        // kms: TODO
        break;

      case EXPR:
        // stamp 먼저 저장 후 동작이므로 stampHist만 저장
        break;
    }

    // kms: TODO 회원정보 스탬프 갱신. 멤버십 구조 변경 후 개발
    UserInfo userInfo = stampHist.getUserInfo();
    UserCard userCard = userInfo.getUserCard();
    userCard.setStampCnt(userCard.getStampCnt() + stampHist.getCnt());
    userCard.setStampTotalCnt(userCard.getStampTotalCnt() + stampHist.getCnt());
    userInfoRepository.save(userInfo);

//    UserInfo userInfo =stampHist.getUserInfo();
//    userInfo.setScore(userInfo.stamp);

    // 쿠폰 개수가 충분하면 cpnIssu 추가
//    CpnIssu cpnIssu = new CpnIssu();
//    cpnIssu.setStampHist(stampHist);
//    cpnIssuService.create(cpnissu);

    return newStampHist;
  }

//  /**
//   * 정보 갱신
//   */
//  @Transactional
//  public StampHist update(StampHistUpdateDto stampHistUpdateDto, StampHist existStampHist) {
//
//    // 입력값 대입
//    this.modelMapper.map(stampHistUpdateDto, existStampHist);
//
//    // 데이터베이스 값 갱신
//    this.stampHistRepository.save(existStampHist);
//
//    return existStampHist;
//  }


  /**
   * 스탬프 이력 취소 처리. 이력 취소 후 회원 정보 반영
   */
  @Transactional
  public void delete(StampHist stampHist) {
    // 스탬프 이력 취소
    stampHist.setCancelRegDttm(ZonedDateTime.now());
    stampHistRepository.save(stampHist);

    // 회원 스탬프 개수 복원
    int cancelStampCnt = 0; // 취소한 스탬프 개수
    switch (stampHist.getStampHistTypeCd()) {
      case RSRV:
      case D_RSRV:
        if (stampHist.getCnt() > stampHist.getUserInfo().getUserCard().getStampCnt()) {
          // TODO 취소할 스탬프보다 현재 가진 스탬프가 적다면 쿠폰 발급 취소
          int reqCnt = stampHist.getCnt() -
              stampHist.getUserInfo().getUserCard().getStampCnt(); // 취소에 모자란 스탬프 개수
          QCpn qCpn = QCpn.cpn;
          BooleanBuilder booleanBuilder = new BooleanBuilder();
          booleanBuilder.and(qCpn.userInfo.id.eq(stampHist.getUserInfo().getId())); // 본인
          booleanBuilder.and(qCpn.cpnSttsCd.eq(CpnSttsCd.AVAIL)); // 사용 가능한 쿠폰
          booleanBuilder.and(qCpn.cpnIssu.cpnIssuRule.cpnIssuRuleCd.eq(CpnIssuRuleCd.S)); // 스탬프 적립으로 발급한 쿠폰


            // che2 : 일단 커맨드 처리
//          for (Cpn cpn :
//              cpnRepository.findAll(
//                  booleanBuilder, PageRequest.of(0, 100, new QSort(qCpn.cpnIssu.regDttm.asc())))) {
//            cpnService.delete(cpn);
//            int stampMaxCnt = cpn.getCpnMaster().getBrand().getBrandSetting().getStampMaxCnt();
//            reqCnt -= stampMaxCnt;
//            cancelStampCnt += stampMaxCnt;
//            // reqCnt 만큼 취소하면 종료
//            if (reqCnt <= 0) {
//              break;
//            }
//          }
        }
        break;
      default:
        // 스탬프 쿠폰이 아니면 취소 처리만 하고 끝
        break;
    }

    // stampHist 스탬프 개수 만큼 회원정보에서 차감
    // cnt 만큼 회원 스탬프에서 -(스탬프가 사용되었다면 cnt는 -이므로 -1*-1 로 가산이 된다)
    UserCard userCard = stampHist.getUserInfo().getUserCard();
    userCard.setStampCnt(userCard.getStampCnt() - stampHist.getCnt());
    userCard.setStampTotalCnt(userCard.getStampTotalCnt() - stampHist.getCnt());
    userInfoRepository.save(stampHist.getUserInfo());

    if (stampHist.getCpnIssu() != null) { // 스탬프 적립하자마자 발급한 쿠폰이 있다면
      // 발급한 쿠폰 취소 처리
      cpnIssuService.delete(stampHist.getCpnIssu());
    }

    // stampHist 에 해당하는 stamp 정보 변경
    for (Stamp stamp : stampRepository.findByStampHist_Id(stampHist.getId())) {
      stampService.delete(stamp);
    }
  }

}
