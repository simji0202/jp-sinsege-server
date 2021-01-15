package kr.co.paywith.pw.data.repository.mbs.delng;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import kr.co.paywith.pw.data.repository.enumeration.DelngTypeCd;
import kr.co.paywith.pw.data.repository.mbs.delngPayment.DelngPaymentDto;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class DelngDto {

    /**
     * 승인번호 (POS , WEBAPP )
     * 페이위드에서 생성해서 거래별 거래 체크
     */
    private String confmNo;

    /**
     *
     * 단말기에서 받는 영수증 번호 (POS 사용시만 사용 )
     */
    private String trmnlDelngNo;

    /**
     * 거래 구분 코드
     * POS : confmNo, trmnlDelngNo, mrhstTrmnl
     * APP :
     *
     */
    @Enumerated(EnumType.STRING)
    private DelngTypeCd delngTypeCd;


    /**
     * 거래 일시. 현장에서 실제 거래가 발생한 시각
     */
    private ZonedDateTime delngDttm;

    /**
     * 결재 금액 ( +- 총합 )
     * 회원이 저장한다면, 조작을 막기 위해 검증을 함.
     */
    private int delngAmt;


    // kms: 구매 목록을 같이 저장하고, 구매 이력 조회 시 상품 정보도 보여야 하므로 활성
    /**
     * 거래 상품 목록. 서버에 저장할 때는 json 형식으로 저장한다
     */
    private String  delngGoodsListJson;


    // 선불카드 / 쿠폰 / 스템프 번호 ////

    /////// 쿠폰 사용에 대한 관련 항목 start/////
    /**
     * 사용한 금액 쿠폰
     */
    private Integer cpnId ;

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
    private UserInfo  userInfo;

    /**
     * 가맹점 단말기. 매장에서 저장한 거래라면 단말기 정보가 있음 (POS, PW)
     */
    private String  mrhstTrmnlId;

    /**
     * 거래 매장 아이디
     */
    private String  mrhstId;

    /**
     * 거래 매장 이름
     */
    private String  mrhstNm;
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
     * 거래 상세(물품) 목록
     */
    private List<DelingGoodsDto> delngGoodsList = new ArrayList<>();

    /**
     * 결제 정보
     */
    private List<DelngPaymentDto>  delngPaymentList = new ArrayList<>();

}
