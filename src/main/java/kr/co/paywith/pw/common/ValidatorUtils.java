package kr.co.paywith.pw.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;

@Slf4j
public class ValidatorUtils {

  /**
   * 모든 값이 Null 이면 오류
   */
  public static void checkString(
      String fieldValue, String fieldNm, Errors errors, boolean checkNull, Integer minLength,
      Integer maxLength) {
    if (checkNull) {
      if (fieldValue == null) {
        errors.reject("fieldNm" + "오류", "값이 비어있습니다");
      }
    }
    if (minLength != null) {
      if (fieldValue.length() < minLength) {
        errors.reject("fieldNm" + "오류", "길이가 " + minLength + "보다 짧습니다");
      }
    }
    if (maxLength != null) {
      if (fieldValue.length() > maxLength) {
        errors.reject("fieldNm" + "오류", "길이가 " + maxLength + "를 초과합니다");
      }
    }
  }

}
