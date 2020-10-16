package kr.co.paywith.pw.data.repository.message;


import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import kr.co.paywith.pw.common.NameDescription;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;


@Data
@Entity
@DynamicUpdate
public class MsgTemplate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 100)
  private String msgTemplateNm;

  @Enumerated(EnumType.STRING)
  private MsgTypeCd msgTypeCd;

  @Column(length = 1000)
  private String msgSj;

  @Lob
  @NameDescription("메세지 내용 ")
  private String msgCn;

  @NameDescription("알림톡 템플릿코드 ")
  private String altTemplateCode;

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
