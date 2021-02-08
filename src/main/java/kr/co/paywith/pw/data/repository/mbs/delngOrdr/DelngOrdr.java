package kr.co.paywith.pw.data.repository.mbs.delngOrdr;

import java.time.LocalDateTime;
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

  @NameDescription("접수시각")
  private LocalDateTime acceptDttm;

  @NameDescription("완료시각")
  private LocalDateTime compDttm;

  @NameDescription("취소시각")
  private LocalDateTime cancelDttm;

  @NameDescription("요청사항")
  private String reqCn;

  @NameDescription("거래 주문_좌석_일련번호")
  @OneToOne
  private DelngOrdrSeatTimetable delngOrdrSeatTimetable;

  @NameDescription("연락처")
  private String mobileNum;

}
