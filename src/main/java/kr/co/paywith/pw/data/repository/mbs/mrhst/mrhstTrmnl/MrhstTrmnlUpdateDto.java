package kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl;

import kr.co.paywith.pw.data.repository.enumeration.PosAvailFnCd;
import kr.co.paywith.pw.data.repository.enumeration.PosTypeCd;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * 가맹점 단말기
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MrhstTrmnlUpdateDto {

    /**
     * 가맹점 단말기 일련번호
     */
    private Integer Id;

    /**
     * 가맹점
     */
    private Mrhst mrhst;

    /**
     * 단말기 번호
     */
    private String trmnlNo;

    /**
     * 단말기 명
     */
    private String trmnlNm;

    /**
     * 사용 여부
     */
    private Boolean activeFl = true;

    /**
     * 주문 서비스 연동 가능 여부
     * 현재 한 매장당 주문서비스 연동 기기는 하나만 있어야 한다.
     */
    private Boolean ordrAvailFl = false;

    /**
     * 단말기 POS 종류
     * 결제 시 ChrgSetleChnlCd가 STR 일 경우 이 타입으로 변경
     * null일 경우에는 STR 상태로 유지
     */
    @Enumerated(EnumType.STRING)
    private PosTypeCd posTypeCd = PosTypeCd.STR;

    /**
     * 웹포스 로그인 아이디
     */
    private String userId;

    /**
     * 웹포스 로그인 암호
     */
    private String userPw;

    /**
     * 푸시 키. 웹포스 앱 버전등에 사용
     */
    private String pushKey;

    /**
     * 푸시 수신 여부
     */
    private Boolean pushFl = true;

    /**
     * 충전 기능 숨김 여부 posAvailFnCdList 에 통합
     */
    private Boolean chrgHiddenFl = true;

    /**
     * POS에서 사용가능한 기능
     */
    @Enumerated(EnumType.STRING)
    private List<PosAvailFnCd> posAvailFnCdList;

    /**
     * 최근 로그인 일시
     */
    private ZonedDateTime lastLoginDttm;

    /**
     * 최근 로그인 일시
     */
    private String lastLoginIp;


}