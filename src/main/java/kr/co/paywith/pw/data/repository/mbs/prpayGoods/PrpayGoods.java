package kr.co.paywith.pw.data.repository.mbs.prpayGoods;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.enumeration.PrpayOperCd;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class PrpayGoods {

  /**
   * 선불카드 종류 일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("식별번호")
  private Integer id;

  /**
   * 선불카드 종류 이름
   */
  private String prpayGoodsNm;

  /**
   * 선불카드 이미지 웹 경로
   */
  private String imgUrl;

  /**
   * 선불카드 제공(구분) 코드
   */
  @Enumerated(EnumType.STRING)
  private PrpayOperCd prpayOperCd;

  /**
   * 사용 여부
   */
  private Boolean activeFl;

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


  @NameDescription("갱신담당자")
  private String updateBy;

  @NameDescription("등록담당자")
  private String createBy;

}