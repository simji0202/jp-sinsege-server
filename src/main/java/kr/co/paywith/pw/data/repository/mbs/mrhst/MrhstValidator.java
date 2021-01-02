package kr.co.paywith.pw.data.repository.mbs.mrhst;


import kr.co.paywith.pw.component.ValidatorUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class MrhstValidator {


  public void validate(MrhstDto mrhstDto, Errors errors) {

    ValidatorUtils.checkString(mrhstDto.getCorpNo(), "사업자 번호", errors, false, 0, 100);

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  public void validate(MrhstUpdateDto mrhstUpdateDto, Errors errors) {

    ValidatorUtils.checkString(mrhstUpdateDto.getCorpNo(), "사업자 번호", errors, false, 0, 100);

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  public void validate(MrhstPwUpdateDto mrhstPwUpdateDto, Errors errors) {


    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }


}
