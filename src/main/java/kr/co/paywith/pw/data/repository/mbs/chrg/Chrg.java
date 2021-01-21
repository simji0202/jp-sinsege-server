package kr.co.paywith.pw.data.repository.mbs.chrg;

import com.opencsv.bean.CsvBindByName;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.ChrgType;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
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
public class Chrg {


    /**
     * 충전이력 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;

    /**
     * 서버 승인번호
     */
    @CsvBindByName(column = "ConfirmationNo")
    private String confmNo;

    /**
     * 단말기 영수증 번호 (POS 사용시만 사용 )
     */
    @CsvBindByName(column = "POSMessageNo")
    private String trmnlDelngNo;

    /**
     * 결제 장소(채널)
     */
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    @CsvBindByName(column = "PayPlace")
    private ChrgType chrgType; // 매장 / PG 구분

    /**
     * 충전일시. 매장 거래 시간 등 실제 거래시간.
     *
     * 무통장 결제 등 결제 결과를 나중에 받는다면 chrgDttm POST 타이밍에 없을 수 있다
     *
     * regDttm만 일단 입력되며 우선 저장, 무통장입금 결과 수신 시 chrgDttm 저장하며 후속처리(잔액 충전, 쿠폰 발급 등)를 한다
     */
    @CsvBindByName(column = "PayDatetime")
    private ZonedDateTime chrgDttm; // 매장 거래 시간

    /**
     * 충전 금액
     */
    @CsvBindByName(column = "LoadAmount")
    private int chrgAmt;

    /**
     * 실제 결제 금액. 이벤트 등으로 실제 결제한 금액보다 더 충전해 줄 수 있으므로, 별도로 필드 관리
     */
    @CsvBindByName(column = "PayAmount")
    private int payAmt;

    /**
     * 결제 승인번호(POS, PG 업체 쪽에서 받는 번호)
     */
    @CsvBindByName(column = "PayNo")
    private String setleConfmNo;

    /**
     * 충전 회원(금액 가산되는 회원)
     */
     @ManyToOne
     private UserInfo userInfo;

    /**
     * 가맹점 단말기. 매장에서 저장한 충전이라면 단말기 정보가 있음 (POS, PW)
     */
    private Integer mrhstTrmnlId;

    /**
     * 충전 매장 아이디
     */
    private String mrhstId;

    /**
     * 충전 매장 이름
     */
    private String mrhstNm;

//    /**
//     * PG 결제
//     */
//    private Pay pay;

//    /**
//     * 쿠폰 발급 목록. 충전으로 쿠폰이 발급될 때 연결
//     */
//    @OneToMany
//    private List<Cpn> cpns;


    // 공통 항목

    /**
     * 취소한 user
     * 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
     */
    private String cancelBy;

    /**
     * 취소 일시. not null 이면 취소
     */
    @CsvBindByName(column = "CancelDatetime")
    private ZonedDateTime cancelRegDttm;

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

    @NameDescription("갱신담당자")
    private String updateBy;

    @NameDescription("등록담당자")
    private String createBy;


}
