package kr.co.paywith.pw.data.repository.mbs.cpnIssu;

import java.util.ArrayList;
import kr.co.paywith.pw.data.repository.mbs.chrg.Chrg;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnDto;
import kr.co.paywith.pw.data.repository.mbs.cpnMaster.CpnMaster;
import kr.co.paywith.pw.data.repository.mbs.cpnRule.CpnRule;
import kr.co.paywith.pw.data.repository.mbs.stampHist.StampHist;
import kr.co.paywith.pw.data.repository.mbs.use.Use;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

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
   * 쿠폰 발급 명
   */
  private String cpnIssuNm;

  /**
   * 쿠폰 표시(회원에게 노출) 일시
   */
  private ZonedDateTime showDttm = ZonedDateTime.now();

  /**
   * 쿠폰 유효 시작 일시
   */
  private ZonedDateTime validStartDttm = ZonedDateTime.now();

  /**
   * 쿠폰 유효 종료 일시
   */
  private ZonedDateTime validEndDttm = ZonedDateTime.now().plusDays(30);

  /**
   * 쿠폰 발급 수량
   */
  private Integer issuCnt;

  /**
   * 쿠폰 종류
   */
  private CpnMaster cpnMaster;

  /**
   * 발급 쿠폰 목록
   */
  private List<Cpn> cpnList = new ArrayList<>();


  /**
   * 쿠폰 발급 규칙
   */
  private CpnRule cpnRule;

  /**
   * 충전 이력 충전으로 발급한 쿠폰일 때 사용
   */
  private Chrg chrg;

  /**
   * 사용 이력 사용으로 발급한 쿠폰일 때 사용
   */
  private Use use;

}
