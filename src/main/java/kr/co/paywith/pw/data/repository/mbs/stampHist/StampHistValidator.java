package kr.co.paywith.pw.data.repository.mbs.stampHist;

import kr.co.paywith.pw.component.ValidatorUtils;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.enumeration.CpnSttsCd;
import kr.co.paywith.pw.data.repository.mbs.brand.BrandService;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class StampHistValidator {

  @Autowired
  private BrandService brandService;

  public void validate(StampHistDto stampHistDto, Errors errors) {

    ValidatorUtils.checkObjectNull(stampHistDto.getCnt(), "스탬프 개수", errors);
    ValidatorUtils.checkInteger(Math.abs(stampHistDto.getCnt()), "스탬프 개수", errors, true, 1, 99);
    ValidatorUtils.checkString(stampHistDto.getTrmnlNo(), "단말기 번호", errors, false, 1, 30);
    ValidatorUtils.checkObjectNull(stampHistDto.getUserInfo(), "회원 정보", errors);
    ValidatorUtils.checkObjectNull(stampHistDto.getUserInfo().getId(), "회원 정보", errors);
    ValidatorUtils.checkObjectNull(stampHistDto.getStampHistTypeCd(), "스탬프 구분", errors);

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
   * @param stampHist 취소하려는 스탬프 이력
   * @param errors
   */
  public void validate(Account currentUser, StampHist stampHist, Errors errors) {

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

    // che2 : 동작을 위해서 일단 커맨드 처리
//    if (
//        (currentUser.getAdmin() != null &&
//            !brandService.hasAuthorization(
//                currentUser.getAdmin().getBrand(), stampHist.getMrhst().getBrand())) || //
//            (currentUser.getMrhstTrmnl() != null &&
//                !stampHist.getMrhst().getId()
//                    .equals(currentUser.getMrhstTrmnl().getMrhst().getId())) || // 거래 매장일 경우
//            currentUser.getUserInfo() != null
//    ){
//      errors.reject("권한 오류", "권한이 없습니다");
//    }


    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }


}
