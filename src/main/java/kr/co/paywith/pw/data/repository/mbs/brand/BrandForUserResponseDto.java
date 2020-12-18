package kr.co.paywith.pw.data.repository.mbs.brand;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BrandForUserResponseDto {

  private String brandCd;
  private String brandNm;
  private String themeValue;
  private String aosVerNm;
  private String iosVerNm;
  private String aosRefPath;
  private String iosRefPath;
}
