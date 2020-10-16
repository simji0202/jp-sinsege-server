package kr.co.paywith.pw.handler;

import com.google.common.base.Strings;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class PwApiException extends RuntimeException {

  PwError error;
  Map<String, Object> data = null;

  public PwApiException(PwError error) {
    super("" + error.getCode());
    this.error = error;
  }

  public Map<String, Object> toMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("code", Strings.padStart("" + this.error.getCode(), 4, '0'));
    map.put("message", this.error.getMessage());

    if (data != null) {
      map.put("data", data);
    }

    return map;
  }

}
