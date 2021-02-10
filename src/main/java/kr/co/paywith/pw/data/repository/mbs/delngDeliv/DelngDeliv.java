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

  @NameDescription("배달비")
  private Integer delivAmt;

  @NameDescription("발송시각")
  private LocalDateTime sendDttm;

  @NameDescription("수령시각")
  private LocalDateTime rcvDttm;

  @NameDescription("수령자명")
  private String rcvNm;

  @NameDescription("수령주소코드")
  private String rcvAddrCodeCd;

  /**
   * 최초 등록시 rcvAddrCodeCd 로 값 조회 후 넣는다. 이후 운영 중 주소지 확인 등에 사용
   */
  @NameDescription("수령주소코드값")
  private String rcvAddrCodeNm;

  @NameDescription("수령주소")
  private String rcvAddr;

  @NameDescription("요청사항")
  private String reqCn;

}
