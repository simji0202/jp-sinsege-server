package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * 회원의 certKey 출처. certKey와 certTypeCd를 통해 관련 서비스에서 유효성을 확인할 수 있다.
 *
 * KAKAO API 를 사용하면 카카오 회원임을 확인하고 정보 획득 가능
 */
@Getter
public enum CertType implements EnumMapperType {
  GUEST("인증하지 않은 게스트=비회원"),
  CI("본인인증 CI. 모든 본인인증 업체간 공통"),
  PHONE("전화번호 인증"),
  KAKAO("카카오 연동 후 받는 일련번호"),
  ;

  private String title;

  CertType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }

}
