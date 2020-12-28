package kr.co.paywith.pw.data.repository.mbs.chrg;

import com.opencsv.bean.CsvBindByName;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.ChrgSetleChnlCd;
import kr.co.paywith.pw.data.repository.enumeration.ChrgSetleMthdCd;
import kr.co.paywith.pw.data.repository.enumeration.ChrgSetleSttsCd;
import kr.co.paywith.pw.data.repository.mbs.chrgMass.ChrgMass;
import kr.co.paywith.pw.data.repository.mbs.cpnIssu.CpnIssu;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl.MrhstTrmnl;
import kr.co.paywith.pw.data.repository.mbs.prpay.Prpay;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

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
     * 입력받은 선불카드 번호(기록용)
     */
    @CsvBindByName(column = "CardNo")
    private String prpayNo;
    /**
     * 발신 단말기 번호(기록용)
     */
    @CsvBindByName(column = "POSNo")
    private String trmnlNo;
    /**
     * 단말기 영수증 번호
     */
    @CsvBindByName(column = "POSMessageNo")
    private String trmnlDelngNo;


    /**
     * 취소 일시. not null 이면 취소
     */
    @CsvBindByName(column = "CancelDatetime")
    private ZonedDateTime cancelRegDttm;

    /**
     * 충전 금액
     */
    @CsvBindByName(column = "LoadAmount")
    private Integer chrgAmt;

    /**
     * 충전 이전 유효 기간. 취소 시 유효기간 복원에 사용
     */
    private ZonedDateTime chrgOldValidDttm;

    /**
     * 충전 이벤트 후 선불카드 잔액
     * <p>
     * TODO 충전 이전 금액이라면 해당 로직 수정해야 함
     */
    private Integer usePosblAmt;

    /**
     * 실제 결제 금액(충전금액과의 차이는 포인트)
     */
    @CsvBindByName(column = "PayAmount")
    private Integer setleAmt;
    /**
     * 결제 승인번호(PG 쪽에서 받는 번호)
     */
    @CsvBindByName(column = "PayNo")
    private String setleConfmNo;
    /**
     * 승인일시. 매장 거래 시간 등 실제 거래시간
     */
    @CsvBindByName(column = "PayDatetime")
    private ZonedDateTime confmDttm = ZonedDateTime.now(); // 매장 거래 시간
    /**
     * 업체 코드. 현금과 각 카드사를 구분하는 코드. PG업체마다 다를 수 있음
     */
    @CsvBindByName(column = "PayType")
    private String chrgSetleInsttCd; // 현금 / 카드사 구분

    /**
     * 결제 장소(채널)
     */
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    @CsvBindByName(column = "PayPlace")
    private ChrgSetleChnlCd chrgSetleChnlCd; // 매장 / PG 구분

    /**
     * 결제 진행 상태. PG는 결제 이전 PRE 상태로 먼저 추가
     */
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    @CsvBindByName(column = "PayStatus")
    private ChrgSetleSttsCd chrgSetleSttsCd; // 결제 상태

    /**
     * 결제 방법
     */
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    @CsvBindByName(column = "PayMethod")
    private ChrgSetleMthdCd chrgSetleMthdCd;

    /**
     * 선불카드
     */
    @ManyToOne
    private Prpay prpay;


    /**
     * pw-proxy 연동에서 사용하는 거래 ID
     */
    // @Column(length = 100, table = "CHRG_HIST_PROXY")
    private String proxyTid;


    // che  확인이 필요, 매장아이디와 매장 단말기가 중복되어 엔티티에 설정 필요 여부
    /**
     * 매장
     */
    @ManyToOne
    private Mrhst mrhst;

    /**
     * 매장 단말기
     */
    @ManyToOne
    private MrhstTrmnl mrhstTrmnl;


    /**
     * 일괄 충전
     */
    @ManyToOne
    private ChrgMass chrgMass;


    /**
     * 쿠폰 발급 목록. 충전으로 쿠폰이 발급될 때 연결
     */
    @OneToMany
    private List<CpnIssu> cpnIssuList;


    // 공통 항목
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