package kr.co.paywith.pw.data.repository.mbs.brand;


import kr.co.paywith.pw.component.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class BrandValidator {

  @Autowired
  private BrandRepository brandRepository;

  public void validate(BrandDto brandDto, Errors errors) {

    // 환경 변수 값이 유효한 json 문자열인지 확인
    String envValueMapStr = brandDto.getEnvValueMap();
    if (envValueMapStr != null && !isValidJson(envValueMapStr)) {
      errors.reject("형식 오류", "환경변수 값이 JSON 형식이 아닙니다");
    }

    ValidatorUtils.checkObjectNull(brandDto.getBrandCd(), "브랜드 코드", errors);

    if (brandRepository.findByBrandCd(brandDto.getBrandCd()).isPresent()) {
      errors.reject("중복 데이터", "브랜드코드가 중복되었습니다");
    }
    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  public void validate(BrandUpdateDto brandDto, Brand existBrand, Errors errors) {

    // 환경 변수 값이 유효한 json 문자열인지 확인
    String envValueMapStr = brandDto.getEnvValueMap();
    if (envValueMapStr != null && !isValidJson(envValueMapStr)) {
      errors.reject("형식 오류", "환경변수 값이 JSON 형식이 아닙니다");
    }
    ValidatorUtils.checkObjectNull(brandDto.getBrandCd(), "브랜드 코드", errors);

    if (brandRepository.findByBrandCdAndIdNot(brandDto.getBrandCd(), existBrand.getId()).isPresent()) {
      errors.reject("중복 데이터", "브랜드코드가 중복되었습니다");
    }
    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }


  /**
   * 환경 변수 값이 유효한 json 문자열인지 확인
   *
   * @param testStr 테스트할 문자열
   * @return 유효하면 true
   */
  private boolean isValidJson(String testStr) {
    try {
      // JSON {} 형식 확인
      new JSONObject(testStr);
    } catch (JSONException ex) {
      try {
        // JSON [] 형식 확인
        new JSONArray(testStr);
      } catch (JSONException ex1) {
        return false;
      }
    }
    return true;
  }
}
