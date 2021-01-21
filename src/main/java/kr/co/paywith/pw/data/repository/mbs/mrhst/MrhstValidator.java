package kr.co.paywith.pw.data.repository.mbs.mrhst;


import kr.co.paywith.pw.component.ValidatorUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class MrhstValidator {


  public void validate(MrhstDto mrhstDto, Errors errors) {

    ValidatorUtils.checkString(mrhstDto.getCorpNo(), "사업자 번호", errors, false, 0, 100);
    ValidatorUtils.checkString(mrhstDto.getMrhstNm(), "매장명", errors, true, 1, 100);

    if (mrhstDto.getLat() != null || mrhstDto.getLng() != null) { // 위도, 경도 중 하나라도 입력하면 검증 시행
      if (mrhstDto.getLat() == null || mrhstDto.getLng() == null || // 한 값이 null 이면 오류
          !(mrhstDto.getLat() >= 0 && mrhstDto.getLat() <= 90) || // 위도 입력 범위 초과
          !(mrhstDto.getLng() >= 0 && mrhstDto.getLng() <= 180) // 경도 입력 범위 초과
      ) {
        errors.reject("위치 정보 입력 오류", "정확한 위도 경도를 입력하세요");
      }
    }

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  public void validate(MrhstUpdateDto mrhstUpdateDto, Errors errors) {

    ValidatorUtils.checkString(mrhstUpdateDto.getCorpNo(), "사업자 번호", errors, false, 0, 100);
    ValidatorUtils.checkString(mrhstUpdateDto.getMrhstNm(), "매장명", errors, true, 1, 100);

    if (mrhstUpdateDto.getLat() != null || mrhstUpdateDto.getLng() != null) { // 위도, 경도 중 하나라도 입력하면 검증 시행
      if (mrhstUpdateDto.getLat() == null || mrhstUpdateDto.getLng() == null || // 한 값이 null 이면 오류
          !(mrhstUpdateDto.getLat() >= 0 && mrhstUpdateDto.getLat() <= 90) || // 위도 입력 범위 초과
          !(mrhstUpdateDto.getLng() >= 0 && mrhstUpdateDto.getLng() <= 180) // 경도 입력 범위 초과
      ) {
        errors.reject("위치 정보 입력 오류", "정확한 위도 경도를 입력하세요");
      }
    }
    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }
}
