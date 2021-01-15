package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * The UserType enumeration.
 */
@Getter
public enum UserType implements EnumMapperType {
  USER("회원"),
  MRHST("매장"),
  MNGR("관리자");

  private String title;

  UserType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }
}
