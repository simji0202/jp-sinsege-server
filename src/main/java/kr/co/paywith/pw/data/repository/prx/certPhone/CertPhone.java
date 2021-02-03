package kr.co.paywith.pw.data.repository.prx.certPhone;

import java.time.ZonedDateTime;
import javax.persistence.Column;
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
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class CertPhone {

  /**
   * 브랜드 일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * 인증번호 수신한 전화번호
   */
  @Column(length = 12)
  private String mobileNum;

  /**
   * 인증번호 입력 유효
   */
  @Column(length = 20)
  private String certNum;

  /**
   * 팝빌 요청 응답 값
   */
  @Column
  private String popbillResponse;

  /**
   * 인증번호 입력 유효
   */
  @Column
  private ZonedDateTime inputValidDttm;

  /**
   * 인증번호 확인 유효 시간. 번호 검증을 마친 후 부터 이 시간 동안 사용이 가능하다
   */
  @Column
  private ZonedDateTime checkValidDttm;

  private Integer brandId;

  /**
   * 등록 일시
   */
  @UpdateTimestamp
  private ZonedDateTime regDttm;

  /**
   * 부정 요청 모니터링 위해 클라이언트 IP 기록
   */
  @NameDescription("등록IP")
  private String regIp;

}
