package kr.co.paywith.pw.data.repository.mbs.mrhstStaff;

import javax.persistence.Id;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class MrhstStaff { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("일련번호")
	private Integer id;

	@NameDescription("이름")
	private String  nm;

	@NameDescription("매장_일련번호")
	private int  mrhstId;

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
