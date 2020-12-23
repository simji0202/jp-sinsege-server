package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum UserAppOsCd implements EnumMapperType {
  IOS("iOS"),
  AOS("Android"),
  ETC("Etc");

  private String title;

  UserAppOsCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
