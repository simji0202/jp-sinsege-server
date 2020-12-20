package kr.co.paywith.pw.data.repository.mbs.use;

import com.opencsv.bean.CsvBindByName;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.enumeration.UseTypeCd;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl.MrhstTrmnl;
import kr.co.paywith.pw.data.repository.user.grade.Grade;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * 사용 처리
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@Table(name = "USE_HIST")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Use {


    /**
     * 사용 이력 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 승인번호
     */
    @CsvBindByName(column = "ConfirmationNo")
    private String confmNo;
    /**
     * 단말기 번호
     */
    @CsvBindByName(column = "POSNo")
    private String trmnlNo;
    /**
     * 단말기 영수증 번호
     */
    @CsvBindByName(column = "POSMessageNo")
    private String trmnlDelngNo;
    /**
     * 사용 구분 코드
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    @CsvBindByName(column = "Type")
    private UseTypeCd useTypeCd;

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

    /**
     * 취소 일시
     */
    @CsvBindByName(column = "CancelDatetime")
    private ZonedDateTime cancelRegDttm;

    /**
     * 취소 관리자 일련번호. 멤버십 관리 페이지 통해 취소할 때 auth의 adminSn 을 기입한다
     */
    private Integer cancelAdminSn;

    /**
     * 취소 회원 일련번호. 회원이 pw-ordr 통해 취소를 하는 경우 회원 일련번호를 기입한다.
     */
    private Integer cancelUserSn;

    /**
     * 취소 단말기 일련번호. 매장 관리 페이지 통해 취소할 때 auth의
     */
    private Integer cancelMrhstTrmnlSn;

    /**
     * 취소 클라이언트 IP
     */
    private String cancelClientIp;

    /**
     * 서버 승인 일시
     */
    @CsvBindByName(column = "Datetime")
    private ZonedDateTime confmDttm;

    /**
     * 사용 금액
     */
    @CsvBindByName(column = "UseAmount")
    private Integer useAmt;
    /**
     * 포인트 사용 금액
     */
    private Integer usePointAmt = 0;
    /**
     * 사용 이전 유효 기간(사용 기준 유효기간이 바뀔 경우 복원을 위한 용도)
     */
    private ZonedDateTime oldValidDt;


    /**
     * 사용한 쿠폰
     */
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Cpn cpn;

//    /**
//     * 선불카드
//     */
//    @ManyToOne(optional = true, fetch = FetchType.LAZY)
//    private Prpay prpay;


    /**
     * 회원
     */
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private UserInfo userInfo;


    /**
     * 등급
     */
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Grade grade;

    /**
     * 가맹점 일련번호
     */
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private Mrhst mrhst;
    /**
     * 사용 가맹점 일련번호
     */

    /**
     * 가맹점 단말기
     */
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private MrhstTrmnl mrhstTrmnl;


//    /**
//     * 사용 상세(물품) 목록
//     */
//    @OneToMany(mappedBy = "use", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = UseDetail.class)
//    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
//    private List<UseDetail> useDetailList;
//
//    /**
//     * 결제
//     */
//    @OneToOne
//    @JoinColumn(name = "paymentSn", insertable = false, updatable = false, table = "USE_HIST_PAYMENT")
//    private Payment payment;

//    /**
//     * 결제 일련번호
//     */
//    private Long paymentSn;


    @Transient
    private String paymentConfmNo;

}
