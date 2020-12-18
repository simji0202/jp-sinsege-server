package kr.co.paywith.pw.data.repository.mbs.brand;


import kr.co.paywith.pw.data.repository.mbs.enumeration.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 브랜드
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class BrandAuth implements Serializable {

  /**
   * 브랜드 일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * 본인인증 업체 아이디
   */
  private String authId;

  /**
   * 본인인증 업체 암호
   */
  private String authPw;

  /**
   * 본인인증 업체 구분 명
   */
  private String authNm;


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



}
