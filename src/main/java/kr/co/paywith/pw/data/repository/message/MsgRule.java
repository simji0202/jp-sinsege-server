package kr.co.paywith.pw.data.repository.message;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import kr.co.paywith.pw.common.NameDescription;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@Entity
@Table
@DynamicUpdate
public class MsgRule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("메세지ID")
  private Integer id;

  @NameDescription("메세지 사용 여부 ")
  private Boolean activeFl;

  @Column(nullable = false, length = 30)
  @Enumerated(EnumType.STRING)
  @NameDescription("메세지 코드")
  private MsgRuleCd msgRuleCd;

  @OneToOne
  @NameDescription("메세지 템플릿 ")
  private MsgTemplate msgTemplate;


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
