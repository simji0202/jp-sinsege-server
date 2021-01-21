package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum NotifType implements EnumMapperType {
  USER("회원 대상"),
  POS("매장 대상");

  private String title;

  NotifType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
