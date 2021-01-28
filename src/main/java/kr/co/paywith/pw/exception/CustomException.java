package kr.co.paywith.pw.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 기존 프로젝트들 CommonException 은 오류코드를 하나하나 생성해서 지정해야 하므로, 공통화 판단이 어려운 초기에 사용하는 예외.
 *
 * CustomException 에서 반복 사용되는 오류는 묶어서 ErrCd 를 만들어 CommonException으로 대체할 의도.
 */
public class CustomException extends RuntimeException {

  String code;
  String message;
  List<Map<String, String>> errors = null;

  public CustomException(String code, String message) {
    super(code);
    this.message = message;
  }

  public CustomException(String code, String message, List<Map<String, String>> errors) {
    super(code);
    this.message = message;
    this.errors = errors;
  }

  public Map<String, Object> toMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("code", code);
    map.put("message", message);
    map.put("errors", errors);
    return map;
  }
}
