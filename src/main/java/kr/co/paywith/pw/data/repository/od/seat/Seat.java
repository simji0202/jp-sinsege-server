package kr.co.paywith.pw.data.repository.od.seat;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.SeatType;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.od.staff.Staff;
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
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length=20)
    private SeatType seatTypeCd;

    private Integer seatCnt;

    @Column(length=30)
    private String seatNm;

    private boolean rsrvSeatFl;

    @Column(length = 30, updatable=false)
    private String regId;

    @CreationTimestamp
    @Column(updatable=false)
    private LocalDateTime regDttm;

    @Column(length = 30, insertable=false)
    private String updtId;

    @UpdateTimestamp
    private LocalDateTime updtDttm;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Mrhst mrhst;

    @OneToOne
    private Staff staff;


}