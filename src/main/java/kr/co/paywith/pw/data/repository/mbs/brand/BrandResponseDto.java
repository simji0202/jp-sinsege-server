package kr.co.paywith.pw.data.repository.mbs.brand;

import kr.co.paywith.pw.data.repository.enumeration.*;

import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@ToString
public class BrandResponseDto {

  private Integer brandSn;
  private String brandCd;
  private String brandNm;
  private Integer parentBrandSn;
//  private List<BrandResponseDto> subBrandList;
  private Boolean activeFl;
  private ZonedDateTime regDttm;
  private ZonedDateTime updtDttm;
  private String fcmKey;
  private Integer prpayMaxCnt;
  private PrpayOvrRuleCd prpayOvrRuleCd;
//  private String themeValue;
  private ZonedDateTime settingUpdtDttm;
  private DtTypeCd prpayValidPeriodCd;
  private Integer prpayValidPeriod;
  private DtTypeCd stampValidPeriodCd;
  private Integer stampValidPeriod;
  private Integer stampMaxCnt;
  private Boolean offGoodsFl;
  private Boolean useOrdrFl;
  private Boolean exactCardNoFl;
  private String aosVerNm;
  private String iosVerNm;
  private String aosRefPath;
  private String iosRefPath;
  private String authId;
  private String authPw;
  private String authNm;
}
