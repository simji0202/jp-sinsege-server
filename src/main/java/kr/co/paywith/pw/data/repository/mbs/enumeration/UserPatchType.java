package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

@Getter
public enum UserPatchType {
  UPD("Update All"),
  PW("Reset Password"),
  MGRT("정보이전 가입"),
  CHK_MTCH("정보 일치 확인");

  private String title;

  UserPatchType(String title) {
    this.title = title;
  }
}
