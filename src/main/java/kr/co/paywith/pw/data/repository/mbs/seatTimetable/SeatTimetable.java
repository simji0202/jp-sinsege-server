package kr.co.paywith.pw.data.repository.mbs.seatTimetable;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.SeatSttsType;
import kr.co.paywith.pw.data.repository.mbs.mrhstSeat.MrhstSeat;
import kr.co.paywith.pw.data.repository.mbs.mrhstStaff.MrhstStaff;
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
public class SeatTimetable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("일련번호")
  private Integer id;

  @NameDescription("시작시각")
  private LocalDateTime startDttm;

  @NameDescription("종료시각")
  private LocalDateTime endDttm;

  @NameDescription("좌석")
  @ManyToOne
  private MrhstSeat mrhstSeat;

  @NameDescription("직원")
  @ManyToOne
  private MrhstStaff mrhstStaff;

//  @NameDescription("좌석_일련번호")
//  private Integer seatId;
//
//  @NameDescription("직원_일련번호")
//  private Integer staffId;

  @NameDescription("좌석 상태")
  private SeatSttsType seatSttsType = SeatSttsType.AVAIL;

}
