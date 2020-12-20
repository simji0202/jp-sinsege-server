package kr.co.paywith.pw.data.repository.mbs.cpnIssu;


import kr.co.paywith.pw.data.repository.mbs.mrhst.MrhstDto;
import kr.co.paywith.pw.data.repository.mbs.mrhst.MrhstPwUpdateDto;
import kr.co.paywith.pw.data.repository.mbs.mrhst.MrhstUpdateDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CpnIssuValidator {

  public void validate(CpnIssuDto cpnIssuDto, Errors errors) {

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  public void validate(CpnIssuUpdateDto cpnIssuUpdateDto, Errors errors) {


    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

}
