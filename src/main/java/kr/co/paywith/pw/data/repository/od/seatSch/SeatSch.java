package kr.co.paywith.pw.data.repository.od.seatSch;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.ReserveScheduleType;
import kr.co.paywith.pw.data.repository.od.seat.Seat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class SeatSch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ReserveScheduleType rsrvSchTypeCd;

    private String rsrvStartHour;

    private String rsrvEndHour;

    private String rsrvStartMinute;

    private String rsrvEndMinute;

    private Integer rsrvCycleMinute;

    private String rsrvCn;

    private String regId;

    @CreationTimestamp
    private LocalDateTime regDttm;

    private String updtId;

    @UpdateTimestamp
    private LocalDateTime updtDttm;

    @ManyToOne
    private Seat seat;


}