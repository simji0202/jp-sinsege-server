package kr.co.paywith.pw.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;

/**
 * Validator 에서 공통적으로 사용하는 필드 검증 기능
 */
@Slf4j
public class ValidatorUtils {

  /**
   * null인지, 최소, 최대 길이 검증
   */
  public static void checkString(
      String fieldValue, String fieldNm, Errors errors,
      boolean checkNull, Integer minLength, Integer maxLength) {
    if (checkNull) {
      if (fieldValue == null) {
        errors.reject(fieldNm + " 오류", fieldNm + "값이 비어있습니다");
      }
    }
    if (fieldValue != null && minLength != null) {
      if (fieldValue.length() < minLength) {
        errors.reject(fieldNm + " 오류", "길이가 " + minLength + "보다 짧습니다");
      }
    }
    if (fieldValue != null && maxLength != null) {
      if (fieldValue.length() > maxLength) {
        errors.reject(fieldNm + " 오류", "길이가 " + maxLength + "를 초과합니다");
      }
    }
  }

  /**
   * null인지, 최소, 최대 값 검증
   */
  public static void checkInteger(
      Integer fieldValue, String fieldNm, Errors errors,
      boolean checkNull, Integer minValue, Integer maxValue) {
    if (checkNull) {
      if (fieldValue == null) {
        errors.reject(fieldNm + " 오류", fieldNm + "값이 비어있습니다");
      }
    }
    if (fieldValue != null && minValue != null) {
      if (fieldValue < minValue) {
        errors.reject(fieldNm + " 오류", "값이 " + minValue + "보다 작습니다");
      }
    }
    if (fieldValue != null && maxValue != null) {
      if (fieldValue > maxValue) {
        errors.reject(fieldNm + " 오류", "값이 " + maxValue + "를 큽니다");
      }
    }
  }


  /**
   * Object null 체크
   */
  public static void checkObjectNull(
      Object fieldValue, String fieldNm, Errors errors) {
    if (fieldValue == null) {
      errors.reject(fieldNm + " 오류", "값이 비어있습니다");
    }
  }
}
