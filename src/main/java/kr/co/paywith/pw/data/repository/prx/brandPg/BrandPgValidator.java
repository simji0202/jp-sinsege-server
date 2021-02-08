package kr.co.paywith.pw.data.repository.prx.brandPg;

import kr.co.paywith.pw.component.ValidatorUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class BrandPgValidator {


    public void validate(BrandPgDto brandPgDto, Errors errors) {
      ValidatorUtils.checkObjectNull(brandPgDto.getBrand(), "업체", errors);
      ValidatorUtils.checkObjectNull(brandPgDto.getPgType(), "상점 구분", errors);
      ValidatorUtils.checkString(brandPgDto.getMallId(), "상점 아이디", errors, true, 1, 99);
      ValidatorUtils.checkString(brandPgDto.getMallKey(), "상점 키", errors, false, 1, 99);


        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }


    public void validate(BrandPgUpdateDto brandPgUpdateDto, Errors errors) {
      ValidatorUtils.checkObjectNull(brandPgUpdateDto.getBrand(), "업체", errors);
      ValidatorUtils.checkObjectNull(brandPgUpdateDto.getPgType(), "상점 구분", errors);
      ValidatorUtils.checkString(brandPgUpdateDto.getMallId(), "상점 아이디", errors, true, 1, 99);
      ValidatorUtils.checkString(brandPgUpdateDto.getMallKey(), "상점 키", errors, false, 1, 99);


        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

}
