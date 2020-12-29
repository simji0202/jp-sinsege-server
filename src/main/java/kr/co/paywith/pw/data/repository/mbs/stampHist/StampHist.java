package kr.co.paywith.pw.data.repository.mbs.stampHist;

import com.opencsv.bean.CsvBindByName;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.StampHistTypeCd;
import kr.co.paywith.pw.data.repository.mbs.cpnIssu.CpnIssu;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.mbs.use.Use;
import kr.co.paywith.pw.data.repository.user.grade.Grade;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
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
public class StampHist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;

    /**
     * 회원
     */
    @ManyToOne
    private UserInfo userInfo;

    /**
     * 스탬프 이력 구분 코드
     */
    @Enumerated(EnumType.STRING)
    private StampHistTypeCd stampHistTypeCd;

    /**
     * 처리 일시
     */
    private ZonedDateTime setleDttm;
    /**
     * 스탬프 개수
     */
    private Integer cnt = 0;
    /**
     * 요청 단말기 번호
     */
    private String trmnlNo;
    /**
     * 단말기 영수증 번호
     */
    @CsvBindByName(column = "POSMessageNo")
    private String trmnlDelngNo;

    /**
     * 취소 일시
     */
    private ZonedDateTime cancelRegDttm;

    /**
     * 가맹점
     */
    @ManyToOne
    private Mrhst mrhst;

    /**
     * 등급. 통계위한 거래 당시 회원의 등급
     */
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Grade grade;

    /**
     * 사용 이력
     * <p>
     * 스탬프 적립 시 / 스탬프 직접 사용 시 연결(이 둘은 useTypeCd로 구분한다)
     */
    @OneToOne
    private Use use;

    /**
     * 쿠폰 발급
     */
    @OneToOne
    private CpnIssu cpnIssu;

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
