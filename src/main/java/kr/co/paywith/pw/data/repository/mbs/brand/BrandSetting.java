package kr.co.paywith.pw.data.repository.mbs.brand;


import kr.co.paywith.pw.data.repository.enumeration.DtTypeCd;
import kr.co.paywith.pw.data.repository.enumeration.PgTypeCd;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * 브랜드 환경 설정.
 * 비즈니스 로직이나, 외부 서비스 연계 등에 사용하는 설정값을 관리한다.
 * 정책에 따라 사용하지 않을 수 있다
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class BrandSetting {

  /**
   * 브랜드 일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * FCM registration id.
   * 일반 사용자에게 노출되는건 피하는게 좋음.
   */
  private String fcmKey;

  /**
   * 포스 플랫폼 용 FCM registration id.
   * 일반 사용자에게 노출되는건 피하는게 좋음.
   */
  private String posFcmKey;

  /**
   * 최대 소지 가능 선불카드 매수. 0은 무제한
   */
  private Integer prpayMaxCnt = 0;

  /**
   * 최대 소지가능 선불카드 매수 이상 등록할 때, 병합 방법에 관한 규칙
   */
//  @Enumerated(EnumType.STRING)
//  @Column(table = "BRAND_SETTING", length = 10)
//  private PrpayOvrRuleCd prpayOvrRuleCd = PrpayOvrRuleCd.D;

  /**
   * 선불카드 유효기간 단위 코드
   */
  @Enumerated(EnumType.STRING)
  private DtTypeCd prpayValidPeriodCd;

  /**
   * 선불카드 충전 시점 기준 선불카드의 유효기간
   */
  private Integer prpayValidPeriod;

  /**
   * 스탬프 유효기간 단위 코드
   */
  @Enumerated(EnumType.STRING)
  private DtTypeCd stampValidPeriodCd;

  /**
   * 스탬프 유효기간
   */
  private Integer stampValidPeriod;

  /**
   * 스탬프 보상을 할 기준 개수
   *
   * ex> stampMaxCnt 수만큼 소지 시 쿠폰 발행
   */
  private Integer stampMaxCnt;

  /**
   * 선불-스탬프 번호 교차 조회
   *
   * 선불카드 번호 ←→ 스탬프 번호로 착오 조회 시 서버에서 다른 번호 조회할 지 여부
   */
  private Boolean exactCardNoFl = false;

  /**
   * 사업자 번호
   */
  private String corpNo;

  /**
   * 문자 발신인 번호
   */
  private String msgSenderTel;

  /**
   * 팝빌 등 문자 서비스 아이디
   */
  private String msgId;

  /**
   * 가맹점 현금영수증 서비스 가입시 사용할 업태
   */
  private String bizType;

  /**
   * 가맹점 현금영수증 서비스 가입시 사용할 종목
   */
  private String bizClass;

  /**
   * 다날 본인인증 CPID
   */
  private String danalCpid;

  /**
   * 다날 본인인증 CPPWD
   */
  private String danalCppwd;

  /**
   * 남은 선불카드가 없을 때 자동으로 발급할 번호 규칙.
   *
   * 없으면 발급하지 않는다
   */
  private String newPrpayNo;

//  /**
//   * 자동으로 발급할 선불카드 종류
//   */
//  @ManyToOne
//  @JoinColumn(name = "prpayGoodsSn", insertable = false, updatable = false)
//  private PrpayGoods prpayGoods;

  /**
   * 스탬프 적립할 선불카드 사용 / 결제 구매 금액 단위. 0 이상일 경우 단위 금액마다 스탬프를 적립한다
   */
  private Integer stampStdAmt;

  /**
   * 포인트 사용 가능한 최소 금액
   */
  private Integer minUsePointAmt = 0;

  /**
   * 등록 일시
   */
  @CreationTimestamp
  private ZonedDateTime regDttm;

  /**
   * 수정 일시
   */
  @UpdateTimestamp
  private ZonedDateTime updtDttm;



}
