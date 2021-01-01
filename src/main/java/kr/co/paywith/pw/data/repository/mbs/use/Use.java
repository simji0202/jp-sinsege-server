package kr.co.paywith.pw.data.repository.mbs.use;

import com.opencsv.bean.CsvBindByName;
import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import kr.co.paywith.pw.data.repository.enumeration.UseTypeCd;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl.MrhstTrmnl;
import kr.co.paywith.pw.data.repository.mbs.payment.Payment;
import kr.co.paywith.pw.data.repository.mbs.useDetail.UseDetail;
import kr.co.paywith.pw.data.repository.user.grade.Grade;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
public class  Use {


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
     * 취소 일시
     */
    @CsvBindByName(column = "CancelDatetime")
    private ZonedDateTime cancelRegDttm;

    // kms: cancelBy 필드 추가
//    /**
//     * 취소 관리자 일련번호. 멤버십 관리 페이지 통해 취소할 때 auth의 adminSn 을 기입한다
//     */
//    private Integer cancelAdminSn;
//
//    /**
//     * 취소 회원 일련번호. 회원이 pw-ordr 통해 취소를 하는 경우 회원 일련번호를 기입한다.
//     */
//    private Integer cancelUserSn;
//
//    /**
//     * 취소 단말기 일련번호. 매장 관리 페이지 통해 취소할 때 auth의
//     */
//    private Integer cancelMrhstTrmnlSn;

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
    @OneToOne(optional = true, fetch = FetchType.LAZY)
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
     * 등급. 통계위한 거래 당시 회원의 등급
     */
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Grade grade;

    /**
     * 가맹점 일련번호
     */
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private Mrhst mrhst;

    /**
     * 가맹점 단말기
     */
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private MrhstTrmnl mrhstTrmnl;


    // kms: 구매 목록을 같이 저장하고, 구매 이력 조회 시 상품 정보도 보여야 하므로 활성
    /**
     * 사용 상세(물품) 목록
     */
    @OneToMany
    private List<UseDetail> useDetailList;

    // kms: 바로 PG 결제해서 구매하는 경우. Payment는 결제 한 건
    /**
     * 결제
     */
    @OneToOne
    private Payment payment;

//    /**
//     * 결제 일련번호
//     */
//    private Long paymentSn;


    @Transient
    private String paymentConfmNo;

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
     * 추가한 관리자
     * 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
     */
    private String createBy;

    /**
     * 변경한  관리자
     * 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
     */
    private String updateBy;

    /**
     * 취소한 user
     * 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
     */
    private String cancelBy;



}
