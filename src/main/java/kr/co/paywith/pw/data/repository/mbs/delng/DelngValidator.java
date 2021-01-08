package kr.co.paywith.pw.data.repository.mbs.delng;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class DelngValidator {


  public void validate(DelngDto delngDto, Errors errors) {

    // 상품 확인
    // kms: TODO 상품 금액 확인
    // kms: TODO 상품 금액 확인 중 쿠폰의 금액, 적용할 상품 개수 확인

    // 결제 관련 검증
    // kms: TODO 결제 관련 검증

    // 결제 관련 검증
    // kms: TODO 결제 관련 검증


    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

//  public void validate(DelngUpdateDto delngUpdateDto, Errors errors) {
//
//
//    // TODO BeginEventDateTime
//    // TODO CloseEnrollmentDateTime
//  }

}
