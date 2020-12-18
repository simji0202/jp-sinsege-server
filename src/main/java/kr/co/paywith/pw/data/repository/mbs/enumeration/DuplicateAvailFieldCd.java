package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

/**
 * 회원 가입 시 중복가능한 필드
 */
@Getter
public enum DuplicateAvailFieldCd implements EnumMapperType {

  AUTH("CI"),
  EMAIL("이메일"),
  PHONE("휴대폰");

  private String title;

  DuplicateAvailFieldCd(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }
}
