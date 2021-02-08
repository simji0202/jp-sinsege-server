package kr.co.paywith.pw.data.repository.mbs.delngDeliv;

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
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class DelngDeliv {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("일련번호")
  private Integer id;

  @NameDescription("발송시각")
  private LocalDateTime outDttm;

  @NameDescription("수령시각")
  private LocalDateTime inDttm;

  @NameDescription("수령자명")
  private String inNm;

  @NameDescription("수령주소")
  private String inAddr;

  @NameDescription("요청사항")
  private String reqCn;

}
