package kr.co.paywith.pw.data.repository.mbs.stampHist;

import java.time.LocalDateTime;
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
  private BrandRepository brandRepository;

  /**
   * ????????? ?????? / ?????? ?????? / ????????? ????????? ?????? ?????? ??????
   *
   * 1. ????????? ????????? stampHist ?????? ??? stampHist ???????????? stamp??? ????????????
   *
   * 2. ?????? ????????? ?????? ????????? ????????????, ?????? ??????+???????????? ?????? ??? ???????????? ??????. stampHist ??????, stamp ?????? ????????? ??????
   *
   * 3. ????????? ????????? ??? ???????????? ???????????????(??????????????? ????????? ??????), <b>stamp ?????? ?????? ?????? ??? stampHist??? ??????</b>??????
   */
  @Transactional
  public StampHist create(StampHist stampHist) {

    // ?????????????????? ??? ??????
    StampHist newStampHist = this.stampHistRepository.save(stampHist);
    newStampHist.setUserInfo(userInfoRepository.findById(stampHist.getUserInfo().getId()).get());

    switch (newStampHist.getStampHistType()) {
      case RSRV:
      case D_RSRV:// ???????????? ????????? Stamp ??????
        for (int i = 0; i < stampHist.getCnt(); i++) {
          Stamp stamp = new Stamp();
          stamp.setStampHist(newStampHist);

          stampService.create(stamp);
        }
        break;

      case CPN: // ???????????? ?????? ?????? ????????? ????????? ??????????????? ?????? stamp ??????
        for (Cpn cpn : stampHist.getCpnIssu().getCpnList()) {
          int stampCntForCpn = cpnStampCnt;
          for (int i = 0; i < stampCntForCpn; i++) {
            Stamp stamp = stampRepository
                .findTopByStampHist_UserInfo_IdAndStampSttsTypeOrderByExpiredDttmAsc(
                    stampHist.getUserInfo().getId(), StampSttsType.RSRV
                );
            stamp.setStampSttsType(StampSttsType.CPN);
            stamp.setCpnId(cpn.getId());
            stampRepository.save(stamp);
          }
        }
        break;

      case USE: // ???????????? ?????? ????????? ????????? ????????? ?????? ?????? stamp ??????
        // kms: TODO
        break;

      case EXPR:
        // stamp ?????? ?????? ??? ??????????????? stampHist??? ??????

        break;
    }

    UserCard userCard = stampHist.getUserInfo().getUserCard();
    switch (stampHist.getStampHistType()) {
      case RSRV:
      case D_RSRV:
        // ?????? ????????? ?????? ??? ?????? ??????
        // ??????, ??????????????? ?????? ?????? ????????? ????????????
        userCard.setStampTotalCnt(userCard.getStampTotalCnt() + stampHist.getCnt());
        break;
    }
    userCard.setStampCnt(userCard.getStampCnt() + stampHist.getCnt());
    userInfoRepository.save(stampHist.getUserInfo());

    return newStampHist;
  }

//  /**
//   * ?????? ??????
//   */
//  @Transactional
//  public StampHist update(StampHistUpdateDto stampHistUpdateDto, StampHist existStampHist) {
//
//    // ????????? ??????
//    this.modelMapper.map(stampHistUpdateDto, existStampHist);
//
//    // ?????????????????? ??? ??????
//    this.stampHistRepository.save(existStampHist);
//
//    return existStampHist;
//  }


  /**
   * ????????? ?????? ?????? ??????. ?????? ?????? ??? ?????? ?????? ??????
   *
   * 1. ?????? ????????? ????????? stamp??? ??????????????? ????????????
   *
   * 2. ?????? ?????? ????????? ????????? ???????????????, ????????? ????????? RSRV ??? ????????????.
   *
   * 3. ?????? ????????? ?????? ??????
   */
  @Transactional
  public void delete(StampHist stampHist) {
    // ????????? ?????? ??????
    stampHist.setCancelRegDttm(LocalDateTime.now());
    stampHistRepository.save(stampHist);

    // ?????? ????????? ?????? ??????
    int cancelStampCnt = 0; // ????????? ????????? ??????
    switch (stampHist.getStampHistType()) {
      case RSRV:
      case D_RSRV:
        if (stampHist.getCnt() > stampHist.getUserInfo().getUserCard().getStampCnt()) {
          // ????????? ??????????????? ?????? ?????? ???????????? ????????? ????????? ????????? ????????? ????????? ??????
          int reqCnt = stampHist.getCnt() -
              stampHist.getUserInfo().getUserCard().getStampCnt(); // ????????? ????????? ????????? ??????

          // ?????? ?????? ??? ??????????????? ????????? ????????? ????????? ???????????? ????????? ?????? ??????
          // ?????? ??????
          // che2 : ?????? ????????? ??????
          for (Cpn cpn :
              cpnRepository
                  .findByUserInfo_IdAndCpnSttsTypeAndCpnIssu_CpnIssuRule_CpnIssuRuleTypeOrderByRegDttm(
                      stampHist.getUserInfo().getId(), CpnSttsType.AVAIL, CpnIssuRuleType.STAMP
                  )) {
            cpnIssuService.delete(cpn.getCpnIssu());
            // ?????? ????????? ????????? ????????? ?????? ??????(????????? ?????? ??????)
            this.delete(stampHistRepository.findByCpnIssu_Id(cpn.getCpnIssu().getId()));
//            cpnService.delete(cpn);
            reqCnt -= cpnStampCnt;
            cancelStampCnt += cpnStampCnt;
            // reqCnt ?????? ???????????? ??????
            if (reqCnt <= 0) {
              break;
            }
          }
        }

        // stampHist ??? ???????????? stamp ?????? ??????
        for (Stamp stamp : stampRepository.findByStampHist_Id(stampHist.getId())) {
          stampService.delete(stamp);
        }

        break;
      case CPN:
        for (Cpn cpn : stampHist.getCpnIssu().getCpnList()) { // ????????? ????????? ????????? ??????
          cancelStampCnt += cpnStampCnt; // ?????? ????????? ????????? ??????

          for (Stamp stamp : stampRepository.findByCpnId(cpn.getId())) { // ????????? ????????? ?????????
            stamp.setStampSttsType(StampSttsType.RSRV);
            stampRepository.save(stamp);
          }
        }

        break;
      case EXPR: // ?????? ????????? ?????? ??????
        throw new IllegalArgumentException();
      default:
        // ????????? ????????? ????????? ?????? ????????? ?????? ???
        break;
    }

    // stampHist ????????? ?????? ?????? ?????????????????? ??????
    // cnt ?????? ?????? ??????????????? -(???????????? ?????????????????? cnt??? -????????? -1*-1 ??? ????????? ??????)
    UserCard userCard = stampHist.getUserInfo().getUserCard();
    userCard.setStampCnt(userCard.getStampCnt() - stampHist.getCnt());
    switch (stampHist.getStampHistType()) {
      case RSRV:
      case D_RSRV:
        // ?????? ????????? ?????? ??? ?????? ??????????????? ?????? ??????
        // ??????, ??????????????? ?????? ?????? ????????? ????????????
        userCard.setStampTotalCnt(userCard.getStampTotalCnt() - stampHist.getCnt());
        break;
    }

    userInfoRepository.save(stampHist.getUserInfo());
  }

}
