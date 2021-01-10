package kr.co.paywith.pw.data.repository.od.seatUse;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.od.ordrRsrv.OrdrRsrv;
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
public class SeatUse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;

    /*예약시작일시*/
    private LocalDateTime rsrvStartDttm;
    /*예약종료일시*/
    private LocalDateTime rsrvEndDttm;

    private Integer attendCnt;

    private Integer attendKidCnt;

    private Integer attendBabyCnt;

    private String rsrvMemo;

    private String regId;

    @CreationTimestamp
    private LocalDateTime regDttm;

    private String updtId;

    @UpdateTimestamp
    private LocalDateTime updtDttm;

    @ManyToOne
    private Seat seat;

    @ManyToOne
    private OrdrRsrv ordrRsrv;

}