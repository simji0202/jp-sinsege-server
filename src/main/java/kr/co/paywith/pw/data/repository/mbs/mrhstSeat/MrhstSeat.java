package kr.co.paywith.pw.data.repository.mbs.mrhstSeat;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import kr.co.paywith.pw.common.NameDescription;
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
public class MrhstSeat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("일련번호")
  private Integer id;

  @NameDescription("이름")
  private String nm;

  @NameDescription("좌석수")
  private int seatCnt;

  @NameDescription("매장_일련번호")
  private int mrhstId;

  @NameDescription("사용 여부")
  private Boolean activeFl = true;

  @NameDescription("갱신담당자")
  private String updateBy;

  @NameDescription("등록담당자")
  private String createBy;

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
