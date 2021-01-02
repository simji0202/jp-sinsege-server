package kr.co.paywith.pw.data.repository.mbs.stampHist;

import kr.co.paywith.pw.component.ValidatorUtils;
import kr.co.paywith.pw.data.repository.enumeration.CpnSttsCd;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class StampHistValidator {

  @Autowired
  private StampHistRepository stampHistRepository;

  public void validate(StampHistDto stampHistDto, Errors errors) {

    ValidatorUtils.checkInteger(stampHistDto.getCnt(), "스탬프 개수", errors, true, 1, 99);
    ValidatorUtils.checkString(stampHistDto.getTrmnlNo(), "단말기 번호", errors, false, 1, 30);
    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

//  public void validate(StampHistUpdateDto stampHistUpdateDto, Errors errors) {
//
//    // TODO BeginEventDateTime
//    // TODO CloseEnrollmentDateTime
//  }

  /**
   * 스탬프 이력 취소 가능한 지 확인
   *
   * @param stampHistDeleteDto
   * @param errors
   */
  public void validate(StampHistDeleteDto stampHistDeleteDto, Errors errors) {

    StampHist stampHist = stampHistRepository.findById(stampHistDeleteDto.getId()).get();

    // 기취소 여부 확인
    if (stampHist.getCancelRegDttm() != null) {
      errors.reject("취소 오류", "이미 취소된 스탬프 오류");
    }

    switch (stampHist.getStampHistTypeCd()) {
      case RSRV:
      case D_RSRV:
        if (stampHist.getCnt() > stampHist.getUserInfo().getUserStamp().getStampCnt()) {
          // TODO 취소할 스탬프보다 현재 가진 스탬프가 적다면 발급 취소할 수 있는 쿠폰 확인
        }
        break;
      case CPN:
        // 쿠폰발급한 이력인데, 쿠폰이 사용되었는지 확인
        for (Cpn cpn : stampHist.getCpnIssu().getCpnList()){
          if (!cpn.getCpnSttsCd().equals(CpnSttsCd.AVAIL)) {
            errors.reject("취소 오류", "취소할 수 없는 쿠폰이 있습니다");
          }
        }
      default:
        errors.reject("취소 오류", "취소할 수 없는 이력");
    }

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }


}
