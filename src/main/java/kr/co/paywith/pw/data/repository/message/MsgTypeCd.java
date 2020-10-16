package kr.co.paywith.pw.data.repository.message;

public enum MsgTypeCd {
  SMS, // 단문 메시지
  LMS, // 장문 메시지
  MMS, // 멀티미디어 메시지
  XMS, // 단/장문 자동인식 메시지
  KAKAO_ATS, // 카카오 알림톡
  KAKAO_FTS, // 카카오 친구톡
  KAKAO_FMS, // 카카오 친구톡 이미지
  NOTIF, // 푸시 메시지
  EMAIL, // 이메일
}
