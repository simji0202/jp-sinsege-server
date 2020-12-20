package kr.co.paywith.pw.data.repository.mbs.use;


import kr.co.paywith.pw.data.repository.mbs.mrhst.MrhstDto;
import kr.co.paywith.pw.data.repository.mbs.mrhst.MrhstPwUpdateDto;
import kr.co.paywith.pw.data.repository.mbs.mrhst.MrhstUpdateDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UseValidator {


  public void validate(UseDto useDto, Errors errors) {


    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  public void validate(UseUpdateDto useUpdateDto, Errors errors) {


    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

}
