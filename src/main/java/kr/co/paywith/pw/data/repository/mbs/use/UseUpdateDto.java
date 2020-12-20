package kr.co.paywith.pw.data.repository.mbs.use;

import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.enumeration.UseTypeCd;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl.MrhstTrmnl;
import kr.co.paywith.pw.data.repository.user.grade.Grade;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * 사용
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class UseUpdateDto {


    /**
     * 사용 이력 일련번호
     */
    private Long id;
    /**
     * 승인번호
     */
    private String confmNo;
    /**
     * 단말기 번호
     */
    private String trmnlNo;
    /**
     * 단말기 영수증 번호
     */
    private String trmnlDelngNo;
    /**
     * 사용 구분 코드
     */
    @Enumerated(EnumType.STRING)
    private UseTypeCd useTypeCd;



    /**
     * 취소 일시
     */
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
    private ZonedDateTime confmDttm;

    /**
     * 사용 금액
     */
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
    private Cpn cpn;

//    /**
//     * 선불카드
//     */
//    private Prpay prpay;


    /**
     * 회원
     */
    private UserInfo userInfo;


    /**
     * 등급
     */
     private Grade grade;

    /**
     * 가맹점
     */
    private Mrhst mrhst;

    /**
     * 가맹점 단말기
     */
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


    private String paymentConfmNo;

}
