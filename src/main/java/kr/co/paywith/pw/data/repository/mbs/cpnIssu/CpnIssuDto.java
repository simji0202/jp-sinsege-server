package kr.co.paywith.pw.data.repository.mbs.cpnIssu;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpnIssuRule.CpnIssuRule;
import kr.co.paywith.pw.data.repository.mbs.cm.CpnMaster;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * 쿠폰 발급(대장)
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@ToString(exclude = {"chrg", "use", "stampHist"})
public class CpnIssuDto {

  /**
   * 쿠폰 발급 일련번호
   */
  private Integer id;

  /**
   * 쿠폰 발급 명
   */
  private String cpnIssuNm;

  /**
   * 쿠폰 표시(회원에게 노출) 일시
   */
  private LocalDateTime showDttm = LocalDateTime.now();

  /**
   * 쿠폰 유효 시작 일시
   */
  private LocalDateTime validStartDttm = LocalDateTime.now();

  /**
   * 쿠폰 유효 종료 일시
   */
  private LocalDateTime validEndDttm = LocalDateTime.now().plusDays(30);

  /**
   * 쿠폰 발급 수량
   */
  private Integer issuCnt;


  /**
   * 발급 쿠폰 목록
   */
  private List<Cpn> cpnList = new ArrayList<>();

  /**
   * 쿠폰 발급 규칙
   */
  private CpnIssuRule cpnIssuRule;

}
