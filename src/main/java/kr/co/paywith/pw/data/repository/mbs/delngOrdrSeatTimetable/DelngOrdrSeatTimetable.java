package kr.co.paywith.pw.data.repository.mbs.delngOrdrSeatTimetable;

import java.time.LocalDateTime;
import javax.persistence.Id;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.ZonedDateTime;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class DelngOrdrSeatTimetable { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("일련번호")
	private Integer id;

	@NameDescription("시작시각")
	private LocalDateTime  startDttm;

	@NameDescription("종료시각")
	private LocalDateTime  endDttm;

	@NameDescription("직원_일련번호")
	private Integer  staffId;

	@NameDescription("좌석_일련번호")
	private Integer  seatId;

}
