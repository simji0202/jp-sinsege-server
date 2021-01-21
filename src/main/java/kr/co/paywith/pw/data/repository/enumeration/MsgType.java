package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum MsgType implements EnumMapperType {
  SMS("단문 메세지"),
  LMS("장문 메세지"),
  MMS("멀티미디어 메시지"),
  XMS("단/장문 자동인식 메시지"),
  KAKAO_ATS("카카오 알림톡"),
  KAKAO_FTS("카카오 친구톡"),
  KAKAO_FMS("카카오 친구톡 이미지"),
  NOTIF("푸시 메시지"),
  EMAIL("이메일");

  private String title;

  MsgType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
