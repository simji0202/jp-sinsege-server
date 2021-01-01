package kr.co.paywith.pw.data.repository.mbs.msgRule;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.enumeration.MsgRuleCd;
import kr.co.paywith.pw.data.repository.mbs.msgTemplate.MsgTemplate;
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
public class MsgRule {


  /**
   * 메시지 발송 규칙 일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("식별번호")
  private Integer id;

  /**
   * 생성 후 발송까지 지연시간(분)
   */
  private Integer delayMinute;

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

  /**
   * 메시지 규칙 종류 코드
   */
  @Enumerated(EnumType.STRING)
  private MsgRuleCd msgRuleCd;

  /**
   * 메시지 양식
   */
  @ManyToOne
  private MsgTemplate msgTemplate;


  /**
   * 메시지 양식 외부 서비스 연동 아이디
   */
  private String msgTemplateExtId;

  /**
   * 메시지 변수 JSON
   */
  private String msgJsonValue;

  @NameDescription("갱신담당자")
  private String updateBy;

  @NameDescription("등록담당자")
  private String createBy;

}
