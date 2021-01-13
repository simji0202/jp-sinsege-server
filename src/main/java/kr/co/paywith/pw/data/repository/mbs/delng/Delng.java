package kr.co.paywith.pw.data.repository.mbs.delng;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
import kr.co.paywith.pw.data.repository.enumeration.DelngTypeCd;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.delngGoods.DelngGoods;
import kr.co.paywith.pw.data.repository.mbs.delngPayment.DelngPayment;
import kr.co.paywith.pw.data.repository.mbs.gcct.Gcct;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl.MrhstTrmnl;
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
 * 거래. 클라이언트에서 받는 주문 한 건
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Delng {

    /**
     * 거래 이력 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 승인번호 (POS , WEBAPP )
     * 페이위드에서 생성해서 거래별 거래 체크
     */
    private String confmNo;

    /**
     *
     * 단말기에서 받는 영수증 번호 (POS)
     */
    private String trmnlDelngNo;

    /**
     * 거래 구분 코드
     * POS : confmNo, trmnlDelngNo, mrhstTrmnl
     * APP :
     *
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private DelngTypeCd delngTypeCd;


    /**
     * 가맹점 단말기. 매장에서 저장한 거래라면 단말기 정보가 있음 (POS, PW)
     */
    @ManyToOne
    private MrhstTrmnl mrhstTrmnl;

//
//
//    /**
//     * 취소 클라이언트 IP
//     */
//    private String cancelClientIp;

    /**
     * 거래 일시. 현장에서 실제 거래가 발생한 시각
     */
    private ZonedDateTime delngDttm;

    /**
     * 거래 금액. 상품에 적용한 쿠폰이 있다면 그 할인금액을 제외한 상품금액의 합.
     * delngPaymentList의 합과 같아야 한다
     *
     * 결재 금액 ( +- 총합 )
     * 회원이 저장한다면, 조작을 막기 위해 검증을 함.
     */
    private Integer delngAmt;

    // kms: delngPayment로 이동
//    /**
//     * 쿠폰으로 할인 받는 금액. cpnAmt, cpnRatio 중 주문에 사용된 실제 금액
//     */
//    private Integer cpnAmt;

//    /**
//     * 사용한 금액 쿠폰
//     */
//    @OneToOne
//    private Cpn cpn;

//    /**
//     * 상품권 적용 금액.
//     */
//    private Integer gcctAmt;

//    /**
//     * 상품권. 단순한 금액 상품권(선불카드와 유사)
//     */
//    @OneToOne
//    private Gcct gcct;

    /**
     * 회원
     */
    @ManyToOne
    private UserInfo userInfo;

//
//    /**
//     * 가맹점. 회원이 저장한 정보라면 매장 정보만 있음.
//     */
//    @ManyToOne
//    private Mrhst mrhst;
//
//




    // kms: 구매 목록을 같이 저장하고, 구매 이력 조회 시 상품 정보도 보여야 하므로 활성
    /**
     * 거래 상품 목록
     */
    @Column(columnDefinition = "json")
    private String  delngGoodsList;

//    goods
//       goodsOpt
//       수량

    /**
     * 결제
     */

//    @OneToMany(mappedBy = "delng", cascade = {CascadeType.ALL}, targetEntity = DelngPayment.class)
//    private List<DelngPayment> delngPaymentList = new ArrayList<>();

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

    /**
     * 취소 일시
     */
    private ZonedDateTime cancelRegDttm;



}
