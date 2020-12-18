package kr.co.paywith.pw.data.repository.mbs.enumeration;

import java.util.Arrays;

public enum ErrCd {
  WORKED(0000, "정상작동(오류가 아님)"),
  ERROR(9999, "일반 오류"),
  EXIST_DATA(1000, "기존 데이터 존재"),
  EXIST_REF_DATA(1001, "참조 데이터 존재"),
  EXIST_ID_STOP(1100, "아이디 정지"),
  EXIST_ID_USED(1101, "아이디 사용 중"),
  EXIST_ID_MISMATCH(1102, "아이디 미일치"),
  EXIST_ID_STOPPING(1103, "아이디 탈퇴 신청"),
  EXIST_PW_MISMATCH(1104, "암호 미일치"),
  EXIST_CARD_ISSUED(1201, "중복 카드 생성 요청"),
  EXIST_CARD_REGISTERED(1202, "카드 소지 초과"),
  EXIST_USE_GOODS(1501, "상품구매 이력 존재"),
  TOO_MANY_DATA(1800, "출력 데이터 허용량 초과"),
  NO_DATA(2000, "데이터 없음"),
  NO_PAYMENT(2001, "결제 정보 없음"),
  NO_ID(2101, "아이디 없음"),
  NO_GUEST(2102, "게스트 등급 없음"),
  NO_APP_INFO(2102, "앱 정보 없음"),
  NO_CARD_RECEIVED(2200, "받은 카드 없음"),
  NO_CARD_VALID(2201, "유효 카드 없음"),
  NO_VALID_MRHST(2300, "해당 매장 없음"),
  NO_VALID_TRMNL(2301, "해당 단말기 없음"),
  NO_ORDR_GOODS(2401, "상품 재고 없음"),
  NO_CONNECT_SETTING(3000, "연동에 필요한 정보 미설정"),
  NO_CONNECT(3001, "연동 오류"),
  NO_MRHST_TRANSACTION(3300, "일치 매장 거래 없음"),
  CONNECT_ORDER_INFO(3400, "주문정보 미 수신"),
  CONNECT_ORDER_MRHST_OFF(3401, "미 오픈 매장"),
  CONNECT_ORDER_CATEGORY(3500, "카테고리 미 수신"),
  CONNECT_GOODS_ORDR(3501, "상품정보 미 수신"),
  CONNECT_ORDER_OPT(3502, "옵션정보 미 수신"),
  PG_TOKEN_INVALID(3900, "PG 유효하지 않은 토큰"),
  PG_PAY_INVALID(3901, "PG 유효하지 않은 거래"),
  PG_ETC(3902, "PG 기타 오류"),
  PG_PARAMS(3903, "PG 매개변수 오류"),
  MSG_PARAMETER(4001, "매개변수 오류"),
  MSG_UNDER_POINT(4105, "포인트 초과 차감"),
  MSG_UNDER_SCORE(4106, "점수 초과 차감"),
  MSG_CARD_DISABLED(4200, "카드 비활성"),
  MSG_OVER_CARD(4201, "카드 초과 충전"),
  MSG_UNDER_CARD(4202, "카드 초과 차감"),
  MSG_CHRG_CANCELED(4203, "이미 취소된 충전 거래"),
  MSG_USE_CANCELED(4204, "이미 취소된 사용 거래"),
  MSG_CHRG_UNAVAILABLE(4205, "충전할 수 없는 요청"),
  MSG_PAYMENT_CANCELED(4206, "이미 취소된 결제"),
  MSG_REFUND_ALREADY(4207, "이미 환불된 결제"),
  MSG_OVER_PAYMENT(4208, "결제 금액 부족"),
  MSG_USE_PAYMENT_ALREADY(4209, "이미 사용된 결제"),
  MSG_MRHST_ORDR_UNAVAILABLE(4301, "주문 불가능한 매장"),
  MSG_TRANSACTION_FAIL(4400, "거래 체결 실패"),
  MSG_TRANSACTION_CANCELED(4401, "이미 취소된 주문 거래"),
  MSG_TRANSACTION_PROCESS(4402, "이미 진행 중인 주문"),
  MSG_TRANSACTION_COMPLETE(4403, "이미 완료된 거래"),
  MSG_CANCEL_REWARD_USED(4606, "보상 사용"),
  MSG_STAMP_CANCELED(4601, "이미 취소된 스탬프 거래"),
  MSG_UNDER_STAMP(4602, "스탬프 초과 취소"),
  MSG_UNDER_STAMP_INVALID(4603, "스탬프 초과 무효 시도"),
  MSG_OVER_STAMP(4604, "스탬프 초과 사용"),
  PREVENT_CHRG_METHOD(7201, "금지된 충전 수단"),
  PREVENT_CHNL(7202, "요청 불가능 한 채널"),
  PREVENT_FILE_SIZE(7800, "파일 데이터 허용 초과"),
  DATA_CONSTRAINT(8000, "제약조건 미일치"),
  DATA_FILE(8001, "전송 파일 오류"),
  DATA_FILE_MISMATCH(8002, "불일치 파일 전송"),
  DATA_CONSTRAINT_USER(8100, "회원 제약조건 미일치"),
  DATA_PRPAY(8200, "선불카드 정보 오류"),
  DATA_STAMP(8600, "스탬프 정보 오류"),
  DATA_CPN(8700, "쿠폰 정보 오류"),
  AUTH_NO_TOKEN(9000, "인증 정보 없음"),
  AUTH_INFO_MISMATCH(9001, "인증 정보 미일치"),
  AUTH_TOKEN_EXPIRED(9002, "인증 만료"),
  AUTH_ERROR(9003, "인증 오류");

  private int code;
  private String reasonPhrase;

  ErrCd(int code, String reasonPhrase) {
    this.code = code;
    this.reasonPhrase = reasonPhrase;
  }

  public static ErrCd findByCode(String codeStr) {
    int code = Integer.parseInt(codeStr);
    return Arrays.stream(ErrCd.values())
        .filter(errCd -> errCd.getCode() == code)
        .findAny()
        .orElse(ERROR);
  }

  public int getCode() {
    return this.code;
  }

  public String getReasonPhrase() {
    return reasonPhrase;
  }
}
