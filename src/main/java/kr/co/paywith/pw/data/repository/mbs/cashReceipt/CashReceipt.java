package kr.co.paywith.pw.data.repository.mbs.cashReceipt;


import kr.co.paywith.pw.data.repository.mbs.use.Use;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * 현금영수증
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class CashReceipt {
  /**
   * 현금영수증 일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * 현금영수증 국세청승인번호
   */
  private String orgConfmNo;

  /**
   * 현금영수증 거래일자 yyyyMMdd
   */
  @Column(length = 8)
  private String orgTradeDate;

  /**
   * 현금영수증 발급 한 사용이력
   */
  @ManyToOne
  private Use use;

  /**
   * 사용이력 일련번호
   */
  @Column
  private Long useSn;

  /**
   * 등록 일시
   */
  @CreationTimestamp
  private ZonedDateTime regDttm;

  /**
   * 취소 일시
   */
  private ZonedDateTime cancelRegDttm;

  /**
   * 취소 전송 예약 여부
   */
  private Boolean cancelSendRsrvFl = false;
}
