package kr.co.paywith.pw.data.repository.mbs.stampHist;

import java.time.ZonedDateTime;
import kr.co.paywith.pw.data.repository.enumeration.CpnIssuRuleType;
import kr.co.paywith.pw.data.repository.enumeration.CpnSttsType;
import kr.co.paywith.pw.data.repository.enumeration.StampSttsType;
import kr.co.paywith.pw.data.repository.mbs.brand.BrandRepository;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnRepository;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnService;
import kr.co.paywith.pw.data.repository.mbs.cpnIssu.CpnIssuRepository;
import kr.co.paywith.pw.data.repository.mbs.cpnIssu.CpnIssuService;
import kr.co.paywith.pw.data.repository.mbs.stamp.Stamp;
import kr.co.paywith.pw.data.repository.mbs.stamp.StampRepository;
import kr.co.paywith.pw.data.repository.mbs.stamp.StampService;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import kr.co.paywith.pw.data.repository.user.user.UserInfoRepository;
import kr.co.paywith.pw.data.repository.user.userCard.UserCard;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

@Service
public class StampHistService {

  @Value("${cpn-stamp-cnt}")
  private Integer cpnStampCnt = 10;

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

  @Autowired
  private CpnIssuRepository cpnIssuRepository;

  @Autowired
  private BrandRepository  brandRepository;

  /**
   * 스탬프 적립 / 쿠폰 발급 / 스탬프 만료에 대한 이력 등록
   *
   * 1. 스탬프 적립은 stampHist 저장 후 stampHist 개수만큼 stamp를 생성한다
   *
   * 2. 쿠폰 발급에 대한 스탬프 이력이면, 쿠폰 발급+회원정보 변경 후 수행해야 한다. stampHist 저장, stamp 상태 변경을 한다
   *
   * 3. 스탬프 만료는 각 스탬프가 중요하므로(만료일시를 가지고 있음), <b>stamp 개별 상태 변경 후 stampHist를 저장</b>한다
   */
  @Transactional
  public StampHist create(StampHist stampHist) {

    // 데이터베이스 값 갱신
    StampHist newStampHist = this.stampHistRepository.save(stampHist);

    switch (newStampHist.getStampHistType()) {
      case RSRV:
      case D_RSRV:// 적립이면 새롭게 Stamp 생성
        for (int i = 0; i < stampHist.getCnt(); i++) {
          Stamp stamp = new Stamp();
          stamp.setStampHist(newStampHist);

          stampService.create(stamp);
        }
        break;

      case CPN: // 정책으로 정한 쿠폰 발급에 필요한 스탬프개수 만큼 stamp 변경
        for (Cpn cpn : stampHist.getCpnIssu().getCpnList()) {
          int stampCntForCpn = cpnStampCnt;
          for (int i = 0; i < stampCntForCpn; i++) {
            Stamp stamp = stampRepository.findTopByStampHist_UserInfo_IdAndStampSttsTypeOrderByExpiredDttmAsc(
                stampHist.getUserInfo().getId(), StampSttsType.RSRV
            );
            stamp.setStampSttsType(StampSttsType.CPN);
            stamp.setCpnId(cpn.getId());
            stampRepository.save(stamp);
          }
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
    if (stampHist.getCnt() > 0) {
      // 신규 저장일 때만 총 개수 변경
      // 만료, 쿠폰발급일 때는 현재 개수만 변경한다
      userCard.setStampTotalCnt(userCard.getStampTotalCnt() + stampHist.getCnt());
    }
    userCard.setStampCnt(userCard.getStampCnt() + stampHist.getCnt());
    userInfoRepository.save(userInfo);

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
   *
   * 1. 적립 취소는 관계된 stamp를 순차적으로 삭제한다
   *
   * 2. 쿠폰 발급 스탬프 이력을 취소한다면, 스탬프 상태를 RSRV 로 복원한다.
   *
   * 3. 만료 이력은 취소 불가
   */
  @Transactional
  public void delete(StampHist stampHist) {
    // 스탬프 이력 취소
    stampHist.setCancelRegDttm(ZonedDateTime.now());
    stampHistRepository.save(stampHist);

    // 회원 스탬프 개수 복원
    int cancelStampCnt = 0; // 취소한 스탬프 개수
    switch (stampHist.getStampHistType()) {
      case RSRV:
      case D_RSRV:
        if (stampHist.getCnt() > stampHist.getUserInfo().getUserCard().getStampCnt()) {
          // 취소할 스탬프보다 현재 가진 스탬프가 적다면 이전에 발급한 쿠폰을 추가로 취소
          int reqCnt = stampHist.getCnt() -
              stampHist.getUserInfo().getUserCard().getStampCnt(); // 취소에 모자란 스탬프 개수

          // 본인 쿠폰 중 사용가능한 스탬프 쿠폰을 무효화 시키면서 스탬프 배수 복원
          // 다시 활성
            // che2 : 일단 커맨드 처리
          for (Cpn cpn :
              cpnRepository.findByUserInfo_IdAndCpnSttsTypeAndCpnIssu_CpnIssuRule_CpnIssuRuleTypeOrderByRegDttm(
                  stampHist.getUserInfo().getId(), CpnSttsType.AVAIL, CpnIssuRuleType.STAMP
              )) {
            cpnIssuService.delete(cpn.getCpnIssu());
            // 쿠폰 발급과 연결된 스탬프 이력 취소(스탬프 복원 표시)
            this.delete(stampHistRepository.findByCpnIssu_Id(cpn.getCpnIssu().getId()));
//            cpnService.delete(cpn);
            reqCnt -= cpnStampCnt;
            cancelStampCnt += cpnStampCnt;
            // reqCnt 만큼 취소하면 종료
            if (reqCnt <= 0) {
              break;
            }
          }
        }

        // stampHist 에 해당하는 stamp 정보 변경
        for (Stamp stamp : stampRepository.findByStampHist_Id(stampHist.getId())) {
          stampService.delete(stamp);
        }

        break;
      case CPN:
        for (Cpn cpn: stampHist.getCpnIssu().getCpnList()) { // 스탬프 이력과 관계된 쿠폰
          cancelStampCnt += cpnStampCnt; // 다시 복원된 스탬프 개수

          for (Stamp stamp : stampRepository.findByCpnId(cpn.getId())) { // 쿠폰과 관계된 스탬프
            stamp.setStampSttsType(StampSttsType.RSRV);
            stampRepository.save(stamp);
          }
        }

        break;
      case EXPR: // 만료 이력은 취소 불가
        throw new IllegalArgumentException();
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
  }

}
