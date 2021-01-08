package kr.co.paywith.pw.data.repository.mbs.gcct;


import kr.co.paywith.pw.component.ValidatorUtils;
import kr.co.paywith.pw.data.repository.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class GcctValidator {

  @Autowired
  private GcctRepository gcctRepository;


  public void validate(GcctDto gcctDto, Errors errors) {

    // 필수값 확인
    // 회원
    ValidatorUtils.checkObjectNull(gcctDto.getToUserInfo(), "회원", errors);
    ValidatorUtils.checkObjectNull(gcctDto.getToUserInfo().getId(), "회원", errors);
    // 상품
    // kms: TODO od.mobileGoods 만들면 활성
//        ValidatorUtils.checkObjectNull(gcctDto.getMobileGoods(), "상품", errors);
//        ValidatorUtils.checkObjectNull(gcctDto.getMobileGoods().getId(), "상품", errors);

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

//    public void validate(GcctUpdateDto gcctUpdateDto, Errors errors) {
//
//
//        // TODO BeginEventDateTime
//        // TODO CloseEnrollmentDateTime
//    }

  /**
   * 상품권 취소 가능 여부 검증
   *
   * @param currentUser 요청 회원
   * @param gcct 취소하려는 상품권
   * @param errors
   */
  public void validate(Account currentUser, Gcct gcct, Errors errors) {

    if (gcct.getCancelRegDttm() == null) {
      errors.reject("취소 오류", "이미 취소된 상품권");
    }

    // 취소 가능한 관리자 확인
    // kms: TODO Gcct.mobileGoods 만들고 나서 활성
//        if (
//            (currentUser.getAdmin() != null &&
//                !brandService.hasAuthorization(
//                    currentUser.getAdmin().getBrand(), gcct.getMrhst().getBrand())) || //
//                (currentUser.getMrhstTrmnl() != null &&
//                    !gcct.getMrhst().getId()
//                        .equals(currentUser.getMrhstTrmnl().getMrhst().getId())) || // 거래 매장일 경우
//                currentUser.getUserInfo() != null
//        ){
//            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//        }

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

}
