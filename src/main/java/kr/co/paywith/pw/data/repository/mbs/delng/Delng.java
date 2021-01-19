package kr.co.paywith.pw.data.repository.mbs.delng;

import kr.co.paywith.pw.data.repository.enumeration.DelngTypeCd;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

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
     * 단말기에서 받는 영수증 번호 (POS 사용시만 사용 )
     */
    private String trmnlDelngNo;

    /**
     * 거래 구분 코드
     * POS : confmNo, trmnlDelngNo, mrhstTrmnl
     * APP :
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private DelngTypeCd delngTypeCd;


    /**
     * 거래 일시. 현장에서 실제 거래가 발생한 시각
     */
    private ZonedDateTime delngDttm;

    /**
     * 거래 금액. 상품에 적용한 쿠폰이 있다면 그 할인금액을 제외한 상품금액의 합.
     * delngPaymentList의 합과 같아야 한다
     * <p>
     * 결재 금액 ( +- 총합 )
     * 회원이 저장한다면, 조작을 막기 위해 검증을 함.
     */
    private int delngAmt;


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
    private String delngGoodsListJson;

    /**
     * 결제
     */
//    @OneToMany
//    private List<DelngPayment> delngPaymentList = new ArrayList<>();
    @Column(columnDefinition = "json")
    private String delngPaymentJson;


    /////// 쿠폰 사용에 대한 관련 항목 start/////
    /**
     * 사용한 금액 쿠폰
     * kms: 상품 금액도 여기에 설정하는지 다시 한번 확인
     */
    private Integer cpnId;

    /**
     * 쿠폰으로 인해서 실직적으로 할인이 적용된 금액
     * 금액 쿠폰 :  할인 금액
     * 1 + 1   :  상품 금액 ( 0 or 상품가격 )
     * 할인 쿠폰 :  할인 금액
     */

    private int cpnAmt;
    /////// 쿠폰 사용에 대한 관련 항목 end /////


    /////////////////// 거래 검증 및 리스트 표시를  위한 정보  start //////////////////////

    /**
     * 거래 회원 아이디
     */
    @ManyToOne
    private UserInfo userInfo;

    /**
     * 가맹점 단말기. 매장에서 저장한 거래라면 단말기 정보가 있음 (POS, PW)
     */
    private Integer mrhstTrmnlId;

    /**
     * 거래 매장 아이디
     */
    private String mrhstId;

    /**
     * 거래 매장 이름
     */
    private String mrhstNm;


    /////////////////// 검증을 위한 정보  end  //////////////////////


    /**
     * 취소한 user
     * 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
     */
    private String cancelBy;

    /**
     * 취소 일시
     */
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

}
