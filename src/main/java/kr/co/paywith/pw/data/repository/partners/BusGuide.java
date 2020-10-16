package kr.co.paywith.pw.data.repository.partners;

import kr.co.paywith.pw.common.NameDescription;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class BusGuide {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("업체번호")
  private Integer id;

  @NameDescription("가이드 이름")
  private String  guideName;

  @NameDescription("연락처")
  private String  guideTel;

  ////// 파트너  정보 ////
  @NameDescription("파트너사")
  @OneToOne
  private Partners partners;

  // 공통 부분
  @LastModifiedDate
  @NameDescription("갱신일")
  private LocalDateTime updateDate;

  @LastModifiedBy
  @NameDescription("갱신담당자")
  private String updateBy;

  @CreatedDate
  @NameDescription("등록일")
  private LocalDateTime createDate;

  @NameDescription("등록담당자")
  private String createBy;

  @NameDescription("변경내용")
  private String updateContent;

}
