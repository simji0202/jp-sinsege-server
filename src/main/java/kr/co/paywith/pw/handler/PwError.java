package kr.co.paywith.pw.handler;

import java.util.Map;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PwError {

  private String code;
  private String message;

  public PwError(Map<String, String> map) {
    this.code = map.get("code");
    this.message = map.get("message");
  }

  public PwError(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return this.code == null ? "9999" : this.code;
  }

  public String getMessage() {
    return this.message == null ? "일반 오류" : this.message;
  }
}
