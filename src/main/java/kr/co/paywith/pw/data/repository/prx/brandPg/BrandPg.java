package kr.co.paywith.pw.data.repository.prx.brandPg;

import kr.co.paywith.pw.data.repository.enumeration.PgType;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * 브랜드에서 사용하는 PG
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class BrandPg {

  /**
   * PG 일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * 상점 아이디. PG 업체에서 발급해주는 식별 값
   */
  private String mallId;

  /**
   * 결제 확인, 취소 등에 사용하는 상점 암호(키)
   */
  private String mallKey;

  /**
   * PG 구분. pw-server 내에서 PG 구분을 위해 사용하고 pw-proxy 에 식별 위해 전달(KICC / NICE)
   */
  @Enumerated(EnumType.STRING)
  private PgType pgType;


  @ManyToOne
  private Brand brand;

  /**
   * 등록일시
   */
  @CreationTimestamp
  private ZonedDateTime regDttm;

  /**
   * 수정일
   */
  @UpdateTimestamp
  private ZonedDateTime updtDttm;
}
