package kr.co.paywith.pw.data.repository.user.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.ZonedDateTime;
import java.util.Set;
import javax.persistence.*;

import kr.co.paywith.pw.data.repository.admin.AdminRole;
import kr.co.paywith.pw.data.repository.enumeration.CertTypeCd;
import kr.co.paywith.pw.data.repository.user.userCard.UserCard;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserInfo {

//
//    등급,  점수,  스템프
//
//    브랜드 정산
//
//    user ---  a브랜드별 ,  b브랜드별
//
//
//      -------------------
//
//    미장원  (브랜드)
//
//        A 점   vvip vip gold
//
//        B 정   vip  gold
//
//        C 점   cvip cgold
//
//      -------------------
//
//               1     :     N                  N      :       1  (브랜)
//    userInfo ---      userInfo-Grade  +  포인트,   ----    Grade  (종류)
//            q
//    userInfo    --          b.g            b.g   브랜드   ---  123        (1--- N종류  )
//
//
//    Grade 등급 마스터 : 1 ---- 10
//    Grade OPtion1  : a,b,c,d,
//    Grade OPtion2  : ㅁㅁ,ㅠ,
//
//
//    Grade_Use (브랜드별 등급 조절 로직 ) : 1 a,b,  2-c,d, 3-a.c
//
//
//            브랜드  -- Grade_Use1,2,3
//
//a브랜드  :  1,2,3
//b브랜드  :  1,2,4,5
//c브랜드  :  1,2,3,5
//         1        :   n
//       userInfo       Brand_use : 브랜드 ,  포인트,        +          Brand_Grade
//                                  c브랜드,  점수  ,        +



    /**
     * 회원 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 회원 아이디.
     *
     */
    private String userId;

    /**
     * 회원 암호
     */
    @JsonIgnore
    private String userPw;

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

    /** 문자(메시지) 수신 동의 */
    private Boolean smsFl;

    /** 마케팅 정보 수신 등 (선택)동의*/
    private Boolean agreFl;

    /**
     * 이메일 주소
     */
    private String emailAddr;

    // 승급관련 항목 start

    /**
     * 누적 획득 점수
     */
    private int totalScore = 0;
    /**
     * 현재 점수
     */
    private int score = 0;

    /**
     * 등급 변동 일시
     */
    private ZonedDateTime gradeUpdtDttm;

//    /**
//     * 회원 등급. TODO GUEST는 grade가 없어도 될지 검토
//     * */
//    @ManyToOne
//    private Grade grade;
//
//    // 승급관련 항목 end

    /**
     * 활성 여부(실제 사용이 가능한 상태)
     */
    private boolean activeFl = true;

    /**
     * 최근 로그인 일시
     */
    private ZonedDateTime lastLoginDttm;


    /**
     * 탈퇴(신청)일
     *
     * 브랜드 별 정책에 따라, 일정 기간 후 activeFl을 false로 처리
     *
     * 적용 패턴
     *
     * 1. activeFl = true, outDttm == null : 일반적인 이용가능 회원
     *
     * 2. activeFl = true, outDttm != null : outDttm 에 탈퇴 신청. activeFl = false로 다시 변경 필요
     *
     * 3. activeFl = false, outDttm != null : outDttm 부터 이용이 불가하게 된 회원
     *
     * 4. activeFl = false, outDttm == null : 현재 존재하지 않는 상태
     *
     */
    private ZonedDateTime outDttm;

    @OneToOne (cascade = CascadeType.ALL)
    private UserCard userCard;

    /**
     * 회원 인증 키
     */
    // kms: 서버-클라이언트 통신 관련한 Authorization 과 헷갈릴 여지가 있어 cert로 이름 변경
    @JsonIgnore
    private String certKey;

    /**
     * 회원 인증 구분
     */
    // kms: 서버-클라이언트 통신 관련한 Authorization 과 헷갈릴 여지가 있어 cert로 이름 변경
    private CertTypeCd certTypeCd;

    // kms: TODO userInfo 테이블에 브랜드 별 회원 정보 만드는 대신 멤버십 관리 필요
//    /**
//     * 원본 회원 아이디(기본 정보를 가지고 있을 회원 아이디) 일련번호
//     */
//    private Integer parentUserSn;

    /**
     * 테스터 여부1100000111
     * 클라이언트와 일부 서버의 기능을 선택적으로 사용가능한 회원인지 확인
     */
    private Boolean testerFl;

    // kms: 일반 회원도 rule 필요한 지
    /**
     * 관리자타입
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AdminRole> roles;

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
