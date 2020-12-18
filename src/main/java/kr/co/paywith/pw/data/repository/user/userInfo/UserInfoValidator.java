package kr.co.paywith.pw.data.repository.user.userInfo;


import kr.co.paywith.pw.data.repository.mbs.brand.BrandDto;
import kr.co.paywith.pw.data.repository.mbs.brand.BrandUpdateDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UserInfoValidator {


  public void validate(UserInfoDto userInfoDto, Errors errors) {


    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  public void validate(UserInfoUpdateDto userInfoUpdateDto, Errors errors) {


    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  public void validate(UserInfoPwUpdateDto userInfoPwUpdateDto, Errors errors) {


    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }


}
