package kr.co.paywith.pw.data.repository.mbs.pointHist;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.PointHistType;
import kr.co.paywith.pw.data.repository.mbs.chrg.Chrg;
import kr.co.paywith.pw.data.repository.mbs.delng.Delng;
import kr.co.paywith.pw.data.repository.mbs.pointRsrvRule.PointRsrvRule;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
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
public class PointHist {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("식별번호")
  private Integer id;

  /**
   * 포인트 양. 사용한 경우 음수
   */
  private Integer pointAmt;

  /**
   * 포인트 이력 구분 코드
   */
  @Column(length = 10)
  @Enumerated(EnumType.STRING)
  private PointHistType pointHistType;

  /**
   * 포인트 적립 규칙
   */
  @ManyToOne
  private PointRsrvRule pointRsrvRule;

  /**
   * 회원
   */
  @ManyToOne
  private UserInfo userInfo;

  // 충전 → 포인트
  /**
   * 관계있는 충전 이력
   *
   * 충전하여 포인트 적립되는 경우
   */
  @OneToOne
  private Chrg chrg;

  // 사용금액 비율 -> 포인트
  /**
   * 관계있는 거래 이력
   */
  @OneToOne
  private Delng delng;

  /**
   * 추가한 관리자 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
   */
  private String createBy;

  /**
   * 변경한  관리자 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
   */
  private String updateBy;

  /**
   * 등록 일시
   */
  @CreationTimestamp
  private ZonedDateTime regDttm;

  /**
   * 취소 일시
   */
  private ZonedDateTime cancelRegDttm;

}
