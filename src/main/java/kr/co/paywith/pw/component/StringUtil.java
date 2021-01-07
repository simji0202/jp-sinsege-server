package kr.co.paywith.pw.component;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

/**
 * String 관련 유틸
 *
 * 번호 문자열 생성
 */
@Component
public class StringUtil {

  final static int VALID_PRPAY_SUM_VALUE = 10;

  /**
   * 규칙에 맞춰 번호 생성. noRule 길이와 동일한 숫자를 나열하고, 추가로 한자리 검증 숫자를 추가한다
   */
  public static String makeNo(String noRule) {
    String prpayNo = "";
    for (int i = 0; i < noRule.length(); i++) {
      char ch = noRule.charAt(i);
      if (Character.isDigit(ch)) {
        prpayNo += ch;
      } else {
        prpayNo += RandomStringUtils.randomNumeric(1);
      }
    }

    int forValid = 0;
    for (int i = 0; i < prpayNo.length(); i++) {
      forValid += Integer.parseInt(String.valueOf(prpayNo.charAt(i)));
    }
    int aaa = forValid % VALID_PRPAY_SUM_VALUE; // 나머지

    prpayNo += ((VALID_PRPAY_SUM_VALUE - aaa) % VALID_PRPAY_SUM_VALUE);

    return prpayNo;
  }
}
