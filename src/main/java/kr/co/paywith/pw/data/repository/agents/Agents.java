package kr.co.paywith.pw.data.repository.agents;

import kr.co.paywith.pw.common.NameDescription;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
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

/**
 *  버스업체
 */
public class Agents {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("버스업체 번호")
  private Integer id;

  @NameDescription("Agent 아이디")
  private String adminId;

  @NameDescription("버스업체명 ")
  private String name;

  @NameDescription("상태")
  private String status;

  @NameDescription("전화번호")
  private String phone;

  @NameDescription("FAX ")
  private String fax;

  @NameDescription("담당자 이름")
  private String managerName;

  @NameDescription("비고")
  private String  remarks;

  @NameDescription("지역")
  private String  areaCd;


  // 공통 부분
  @LastModifiedDate
  @NameDescription("갱신일")
  private LocalDateTime updateDate;

  @NameDescription("갱신담당자")
  private String updateBy;

  @CreatedDate
  @NameDescription("등록일")
  private LocalDateTime createDate;

  @NameDescription("등록담당자")
  private String createBy;

}
