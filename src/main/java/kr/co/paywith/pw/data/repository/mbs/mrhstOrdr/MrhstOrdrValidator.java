package kr.co.paywith.pw.data.repository.mbs.mrhstOrdr;

import kr.co.paywith.pw.component.ValidatorUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class MrhstOrdrValidator {


  public void validate(MrhstOrdrDto mrhstOrdrDto, Errors errors) {
    ValidatorUtils.checkInteger(mrhstOrdrDto.getRsrvUnitMin(), "예약 단위 분", errors, false, 0, 14400);
    // TODO ~Tm1 부터 7까지 시간 단위 검증

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  public void validate(MrhstOrdrUpdateDto mrhstOrdrUpdateDto, Errors errors) {
    ValidatorUtils.checkInteger(mrhstOrdrUpdateDto.getRsrvUnitMin(), "예약 단위 분", errors, false, 0, 14400);
    // TODO ~Tm1 부터 7까지 시간 단위 검증

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

}
