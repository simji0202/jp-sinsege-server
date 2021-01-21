package kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl;

import kr.co.paywith.pw.data.repository.enumeration.PosFnType;
import kr.co.paywith.pw.data.repository.enumeration.PosType;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

/**
 * 가맹점 단말기
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MrhstTrmnlDto {

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
     * 단말기 POS 종류
     * 결제 시 ChrgSetleChnlCd가 STR 일 경우 이 타입으로 변경
     * null일 경우에는 STR 상태로 유지
     */
    @Enumerated(EnumType.STRING)
    private PosType posType = PosType.PW;

    /**
     * 웹포스 로그인 아이디
     */
    private String userId;

    /**
     * 웹포스 로그인 암호
     */
    private String userPw;

    /**
     * 로그인 정보. 로그인 시 클라이언트에서 UUID를 보내오면 저장. 로그아웃 하면 삭제한다.(중복 로그인 방지)
     */
    private String userUuid;

    /**
     * 푸시 키. 웹포스 앱 버전등에 사용
     */
    private String pushKey;

    /**
     * 푸시 수신 여부
     */
    private Boolean pushFl = true;

    /**
     * POS에서 사용가능한 기능
     */
    private List<PosFnType> posFnTypeList;


}
