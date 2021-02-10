package kr.co.paywith.pw.data.repository.mbs.mrhstDelivAmt;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.code.addrCode.AddrCode;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class MrhstDelivAmt {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("일련번호")
  private Integer id;

  private Integer mrhstId;

  //
  // @JsonIgnore
  // kms: AddrCode.mrhstDelivAmtList를 만들면 한번에 지울 수 있는데 활용할지 검토 필요
  /**
   * 주소 코드
   */
  @ManyToOne
  private AddrCode addrCode;

  /**
   * 해당 지역 배달비
   */
  private Integer delivAmt;

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

  @NameDescription("갱신담당자")
  private String updateBy;

  @NameDescription("등록담당자")
  private String createBy;

}
