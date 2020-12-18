package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum PaymentMthdCd implements EnumMapperType {
  CASH("현금", Arrays.asList("00")),
  CARD("신용카드", Arrays.asList("CARD")),
  CARD2("신용카드(법인)", Arrays.asList("CARD2")),
  PHONE("통신사", Arrays.asList("PHONE")),
  BILL("빌링", Arrays.asList("BILL")),
  POINT("포인트", Arrays.asList("POINT")),
  VBANK("가상계좌", Arrays.asList("VBANK")),
  OBANK("오픈뱅킹", Arrays.asList("OBANK")),
  EMPTY("불명", Collections.emptyList());

  @Getter
  private String title;
  private List<String> mthdList;

  PaymentMthdCd(String title, List<String> mthdList) {
    this.title = title;
    this.mthdList = mthdList;
  }

  public static PaymentMthdCd findByInsttCd(String insttCd) {
    if (insttCd == null) {
      return EMPTY;
    } else {
      return Arrays.stream(PaymentMthdCd.values())
          .filter(chrgSetleMthdCd -> chrgSetleMthdCd.hasMthdCd(insttCd))
          .findAny()
          .orElse(EMPTY);
    }
  }

  public boolean hasMthdCd(String cd) {

    return mthdList.stream()
        .anyMatch(mthd -> mthd.equals(cd));
  }

  @Override
  public String getCode() {
    return name();
  }

}
