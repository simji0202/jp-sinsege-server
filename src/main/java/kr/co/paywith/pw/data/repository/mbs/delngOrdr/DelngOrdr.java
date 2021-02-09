package kr.co.paywith.pw.data.repository.mbs.delngOrdr;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.delngOrdrSeatTimetable.DelngOrdrSeatTimetable;
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
public class DelngOrdr {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("일련번호")
  private Integer id;

  /**
   * 시트 사용하지 않는 단순 예약 서비스에서 사용. 회원이 최초 주문 등록할 때 기입
   */
  @NameDescription("예약시각")
  private LocalDateTime rsrvDttm;

  /**
   * 회원이 등록한 주문을 매장에서 접수할 때 매장측에서 설정(update)
   */
  @NameDescription("접수시각")
  private LocalDateTime acceptDttm;

  /**
   * 완료 처리 측에서 설정. 정책마다 다를 수 있음.
   *
   * compDttm 값이 있으면 회원은 취소가 불가능
   */
  @NameDescription("완료시각")
  private LocalDateTime compDttm;

  @NameDescription("취소시각")
  private LocalDateTime cancelDttm;

  @NameDescription("요청사항")
  private String reqCn;

  @NameDescription("거래 주문_좌석_일련번호")
  @OneToOne(cascade = {CascadeType.ALL})
  private DelngOrdrSeatTimetable delngOrdrSeatTimetable;

  @NameDescription("연락처")
  private String mobileNum;

}
