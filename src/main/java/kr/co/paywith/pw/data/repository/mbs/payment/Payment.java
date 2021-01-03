package kr.co.paywith.pw.data.repository.mbs.payment;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.enumeration.PaymentChnlCd;
import kr.co.paywith.pw.data.repository.enumeration.PaymentMthdCd;
import kr.co.paywith.pw.data.repository.enumeration.PaymentSttsCd;
import kr.co.paywith.pw.data.repository.mbs.brandPg.BrandPg;
import kr.co.paywith.pw.data.repository.mbs.use.Use;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.ZonedDateTime;

// kms: 삭제 검토. prx와 서버가 통합되어 바로 활용할 수 있어서 use.payment 대신 prx의 결제 연결 예정
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Payment {

  /**
   * 결제 일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("식별번호")
  private Integer id;

  /**
   * 서버 승인번호
   */
  private String confmNo;

  /**
   * 단말기 영수증 번호
   */
  private String trmnlDelngNo;

  /**
   * 취소 일시. not null 이면 취소
   */
  private ZonedDateTime cancelRegDttm;

  /**
   * 결제 금액
   */
  private Integer paymentAmt = 0;

  /**
   * 환불 금액
   */
  private Integer cancelAmt = 0;

  /**
   * 결제 승인번호(PG 쪽에서 받는 번호)
   */
  private String setleConfmNo;

  /**
   * 결제 방법
   */
  @Column(length = 10)
  @Enumerated(EnumType.STRING)
  private PaymentMthdCd paymentMthdCd;

  /**
   * 업체 코드. 현금과 각 카드사를 구분하는 코드. PG업체마다 다를 수 있음
   */
  private String paymentSetleInsttCd; // 현금 / 카드사 구분

  /**
   * 결제 장소(채널)
   */
  @Enumerated(EnumType.STRING)
  private PaymentChnlCd paymentChnlCd;

  /**
   * 결제 진행 상태. PG는 결제 이전 PRE 상태로 먼저 추가
   */
  @Enumerated(EnumType.STRING)
  private PaymentSttsCd paymentSttsCd = PaymentSttsCd.PRE; // 결제 상태

  /**
   * pw-proxy 연동에서 사용하는 거래 ID
   */
  // kms: TODO 통합 후 같은 값 연결할지 검토 필요
  private String proxyTid;

  /**
   * pw-proxy 연동해서 받은 결과 중 비회원 식별을 위한 이메일(결제 완료 과정에서 입력하는 이메일)
   */
  // kms: TODO 통합 후 같은 값 연결할지 검토 필요
  private String emailAddr;

  /**
   * 승인일시. 매장 거래 시간 등 실제 거래시간
   */
  private ZonedDateTime confmDttm = ZonedDateTime.now(); // 매장 거래 시간

  /**
   * 회원
   */
  @ManyToOne
  private UserInfo userInfo;

  /**
   * 이 결제로 구매한 사용(구매)이력
   *
   * 구매를 하지 않고 결제만 했다면 취소(환불) 처리 등을 해야 한다
   */
  @OneToOne
  private Use use;

  /**
   * 브랜드PG
   */
  @ManyToOne
  // kms: 복수의 pg를 사용할 수 있으므로 Brand 관계된 PG 정보 필요
  // kms: pw-proxy 의 pg 정보 연결
  private BrandPg brandPg;

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
