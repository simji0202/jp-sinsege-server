package kr.co.paywith.pw.data.repository.mbs.delngPayment;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.enumeration.DelngPaymentType;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

// kms: pg 결제 한건 이었으나, 용도 변경 -> 구매한 상품들을 결제하는 결제수단 한가지(배열로 들어가면 복합결제)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@ToString(exclude = {"delng"})
public class DelngPayment {

  /**
   * 결제 일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("식별번호")
  private Integer id;

  /**
   * 금액 지불 방법.
   * 선불카드, PG 결제, 쿠폰
   */
  private DelngPaymentType delngPaymentType;

  /**
   * 결제 금액
   */
  private Integer amt = 0;

  // kms: TODO 선불카드 작성하면 연결
  /**
   * 선불카드
   */
//  @OneToOne
//  private Prpay prpay;

  // kms: TODO PgPay 작성하면 연결
//  /**
//   * PG 결제
//   */
//  @OneToOne
//  private Pay pay;

  /**
   * 등록 일시
   */
  @CreationTimestamp
  private LocalDateTime regDttm;

  /**
   * 수정 일시
   */
  @UpdateTimestamp
  private LocalDateTime updtDttm;


}
