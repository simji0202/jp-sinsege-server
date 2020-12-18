package kr.co.paywith.pw.data.repository.user.userInfo;

import kr.co.paywith.pw.data.repository.admin.AdminRole;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.user.grade.Grade;
import kr.co.paywith.pw.data.repository.user.userApp.UserApp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class UserInfoUpdateDto {

    /**
     * 회원 일련번호
     */
    @Id
    private Integer id;

    private String userId;

    /**
     * 회원 이름
     */
    private String userNm;
    /**
     * 회원 별명
     */
    private String nickNm;

    /**
     * 회원 생년
     */
    private Integer brthY;
    /**
     * 회원 생월
     */

    private Integer brthM;
    /**
     * 회원 생일
     */

    private Integer brthD;
    /**
     * 회원 남자 여부
     */
    private Boolean maleFl;
    /**
     * 휴대폰 번호
     */
    private String mobileNum;
    /**
     * 통신사 코드
     */
    private String userPhoneCd;

    /**
     * 문자(메시지) 수신 동의
     */
    private Boolean smsFl;

    /**
     * 마케팅 정보 수신 등 (선택)동의
     */
    private Boolean agreFl;

    /**
     * 이메일 주소
     */
    private String emailAddr;
    /**
     * 누적 획득 점수
     */
    private Integer scorePlus = 0;
    /**
     * 현재 점수
     */
    private Integer scoreCnt = 0;
    /**
     * 회원 누적 사용 점수
     */
    private Integer pointUsed = 0;
    /**
     * 회원 누적 적립 점수
     */
    private Integer pointChrged = 0;
    /**
     * 회원 등급 일련번호
     */
    private Integer gradeSn;
    /**
     * 혜택을 받은 최종 등급
     * <p>
     * 등급 업 후 등급 업 혜택을 받으면 해당 gradeSn으로 업데이트 한다(강등 후 재차 혜택 받는 것을 방지)
     */
    private Integer lastMaxGradeSn;
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
     * 활성 여부(실제 사용이 가능한 상태)
     */
    private Boolean activeFl = true;
    /**
     * 최근 로그인 일시
     */
    private ZonedDateTime lastLoginDttm;
    /**
     * 등급 변동 일시
     */
    private ZonedDateTime gradeUpdtDttm;

    /**
     * 탈퇴(신청)일
     * <p>
     * 브랜드 별 정책에 따라, 일정 기간 후 activeFl을 false로 처리
     * <p>
     * 적용 패턴
     * <p>
     * 1. activeFl = true, outDttm == null : 일반적인 이용가능 회원
     * <p>
     * 2. activeFl = true, outDttm != null : outDttm 에 탈퇴 신청. activeFl = false로 다시 변경 필요
     * <p>
     * 3. activeFl = false, outDttm != null : outDttm 부터 이용이 불가하게 된 회원
     * <p>
     * 4. activeFl = false, outDttm == null : 현재 존재하지 않는 상태
     */
    private ZonedDateTime outDttm;

    /**
     * 회원 앱 목록
     */
    private List<UserApp> userAppList = new ArrayList<>();

//    /**
//     * 브랜드
//     */
//    private Brand brand;


    /** 회원 등급 ()*/
//    @ManyToOne
    private Grade grade;

    /**
     * 회원 소지 스탬프 개수
     */
    private Integer stampCnt = 0;
    /**
     * 현재 스탬프 적립 시작 일시
     */
    private ZonedDateTime stampStartDttm;
    /**
     * 스탬프 갱신 일시
     */
    @UpdateTimestamp
    private ZonedDateTime stampUpdtDttm;
    /**
     * 회원 스탬프 번호
     */
    private String stampNo;
    /**
     * 카카오 페이 멤버십 스탬프 번호
     */
    private String kakaoStampNo;
    /**
     * 스탬프 누적 획득 개수
     */
    private Integer stampTotalGet = 0;

    /**
     * 회원 인증 키
     */
    private String authKey;

    /**
     * 스탬프 최대 소지 수량
     */

    private Integer stampMaxCnt;

    /**
     * 원본 회원 아이디(기본 정보를 가지고 있을 회원 아이디) 일련번호
     */
    private Integer parentUserSn;

    /**
     * 테스터 여부1100000111
     * 클라이언트와 일부 서버의 기능을 선택적으로 사용가능한 회원인지 확인
     */
    private Boolean testerFl;

    /**
     * 관리자타입
     */
    @Enumerated(EnumType.STRING)
    private Set<AdminRole> roles;


}
