package kr.co.paywith.pw.data.repository.mbs.cpnMaster;


import kr.co.paywith.pw.component.ValidatorUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CpnMasterValidator {


  public void validate(CpnMasterDto cpnMasterDto, Errors errors) {

    ValidatorUtils.checkString(cpnMasterDto.getCpnNm(), "쿠폰 명", errors, true, 1, 100);

    ValidatorUtils.checkInteger(cpnMasterDto.getValidDay(), "유효기간", errors, true, 1, null);
    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  public void validate(CpnMasterUpdateDto cpnMasterUpdateDto, Errors errors) {

    ValidatorUtils.checkString(cpnMasterUpdateDto.getCpnNm(), "쿠폰 명", errors, true, 1, 100);

    ValidatorUtils.checkInteger(cpnMasterUpdateDto.getValidDay(), "유효기간", errors, true, 1, null);

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }


}
