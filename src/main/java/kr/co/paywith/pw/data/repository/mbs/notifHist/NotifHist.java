package kr.co.paywith.pw.data.repository.mbs.notifHist;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.mbs.notif.Notif;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class NotifHist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;

    /**
     * 매장
     */
    @ManyToOne
    private Mrhst mrhst;

    /**
     * 매장 일련번호
     */
    private Integer mrhstSn;

    /**
     * 푸시 메시지
     */
    @ManyToOne
    private Notif notif;

    /**
     * 푸시 일련번호
     */
    private Integer notifSn;

    /**
     * 푸시 전송 이력 일련번호
     */
    private Integer notifHistSn;

    /**
     * 전송 여부
     */
    private Boolean sendFl;

    /**
     * 전송 일시
     */
    private ZonedDateTime sendDttm;

}