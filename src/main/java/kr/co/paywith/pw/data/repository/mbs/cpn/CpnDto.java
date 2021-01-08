package kr.co.paywith.pw.data.repository.mbs.cpn;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import kr.co.paywith.pw.data.repository.enumeration.CpnSttsCd;
import kr.co.paywith.pw.data.repository.mbs.cpnIssu.CpnIssu;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.Data;

/**
 * 가맹점
 */
@Data
public class CpnDto {


  /**
   * 쿠폰 일련번호 (KEY)
   */
  private Integer id;

  /**
   * 쿠폰 번호(바코드 16자리). 쿠폰을 최초 열람할때 번호 생성. 예전 로직과는 순서가 다르므로 참고
   */
  private String cpnNo;

  /**
   * 쿠폰 상태 코드
   */
  @Enumerated(EnumType.STRING)
  private CpnSttsCd cpnSttsCd = CpnSttsCd.AVAIL;

  /**
   * 회원 ( 쿠폰 소유자 )
   */
  private UserInfo userInfo;

  /**
   * 쿠폰 발급(대장) CpnIssu cpu1, cpn2 .....
   */
  private CpnIssu cpnIssu;

  /**
   * 확인 여부
   */
  private Boolean readFl = false;

}
