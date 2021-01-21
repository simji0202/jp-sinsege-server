package kr.co.paywith.pw.data.repository.mbs.cpnIssu;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 쿠폰 발급(대장)
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class CpnIssuUpdateDto {

  /**
   * 쿠폰 발급 명
   */
  private String cpnIssuNm;

  // kms: 이미 표시된 쿠폰 정보 변경하지 않도록 함. 발급 오류의 경우 만료만 할 수 있도록 종료일시만 변경 가능
//    /**
//     * 쿠폰 표시(회원에게 노출) 일시
//     */
//    private ZonedDateTime showDttm = ZonedDateTime.now();
//
//    /**
//     * 쿠폰 유효 시작 일시
//     */
//    private ZonedDateTime validStartDttm = ZonedDateTime.now();

  // che2 : 쿠폰 목록 추가 (20.01.07)
//  /**
//   * 발급 쿠폰 목록
//   */
//  private List<Cpn> cpnList = new ArrayList<>();

  /**
   * 쿠폰 유효 종료 일시
   */
  private ZonedDateTime validEndDttm = ZonedDateTime.now().plusDays(30);

}
