package kr.co.paywith.pw.data.repository.mbs.brand;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;


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
public class BrandApp implements Serializable {

  /**
   * 브랜드 일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * 안드로이드 버전 명
   */
  private String aosVerNm;

  /**
   * 사용 가능 최소 안드로이드 버전 명
   */
  private String minAosVerNm;

  /**
   * iOS 버전 명
   */
  private String iosVerNm;

  /**
   * 사용 가능 최소 iOS 버전 명
   */
  private String minIosVerNm;

  /**
   * 안드로이드 플레이 스토어 경로
   */
  private String aosRefPath;

  /**
   * 애플 앱스토어 경로
   */
  private String iosRefPath;


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
