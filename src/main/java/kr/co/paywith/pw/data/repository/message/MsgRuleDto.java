package kr.co.paywith.pw.data.repository.message;


import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MsgRuleDto {

  private Integer msgRuleSn;

  private Integer delayMinute;

  private Boolean activeFl;

  private MsgTemplate msgTemplate;

  private Integer msgTemplateSn;

  private String msgTemplateExtId;

  private String msgJsonValue;

  @CreationTimestamp
  private ZonedDateTime regDttm;

  @UpdateTimestamp
  private ZonedDateTime updtDttm;


}
