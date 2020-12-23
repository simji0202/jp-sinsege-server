package kr.co.paywith.pw.data.repository.mbs.brand;

import kr.co.paywith.pw.data.repository.enumeration.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public interface BrandInfoMngr {

  Integer getBrandSn();

  String getBrandCd();

  String getBrandNm();

  Integer getParentBrandSn();

  List<BrandInfoMngr> getSubBrandList();

  Boolean getActiveFl();

  ZonedDateTime getRegDttm();

  ZonedDateTime getUpdtDttm();

  String getFcmKey();

  String getPosFcmKey();

  Integer getPrpayMaxCnt();

  PrpayOvrRuleCd getPrpayOvrRuleCd();

  String getThemeValue();

  Integer getUserInfoKeepDay();

  DtTypeCd getPrpayValidPeriodCd();

  Integer getPrpayValidPeriod();

  DtTypeCd getStampValidPeriodCd();

  Integer getStampValidPeriod();

  Integer getStampMaxCnt();

  Boolean getOffGoodsFl();

  Boolean getUseOrdrFl();

  Boolean getExactCardNoFl();

  String getVdTermId();

  String getVdAccessKey();

  PgTypeCd getDefaultPgTypeCd();

  String getCorpNo();

  String getMsgSenderTel();

  String getMsgId();

  String getBizType();

  String getBizClass();

  Integer getBillingMinAmt();

  List<MenuItemCd> getMenuItemCdBMstList();

  List<MenuItemCd> getMenuItemCdSMstList();

  List<MenuItemCd> getEditableMenuItemCdBMstList();

  List<MenuItemCd> getEditableMenuItemCdSMstList();

  List<AvailServiceCd> getAvailServiceCdList();

  List<ChrgSetleMthdCd> getAvailAppChrgSetleMthdCdList();

  List<ChrgSetleMthdCd> getAvailPosChrgSetleMthdCdList();

  String getAosVerNm();

  String getMinAosVerNm();

  String getIosVerNm();

  String getMinIosVerNm();

  String getAosRefPath();

  String getIosRefPath();

  String getAuthId();

  String getAuthPw();

  String getAuthNm();

  String getKakaoId();

  List<AvailBrandFnCd> getAvailBrandFnCdList();

  List<DuplicateAvailFieldCd> getDuplicateAvailFieldCdList();

  String getImgUrl();

  String getLogoImgUrl();

  String getNewPrpayNo();

  Integer getPrpayGoodsSn();

  Integer getStampStdAmt();

  Integer getMinUsePointAmt();

  Map<String, String> getEnvValueMap();

}
