package kr.co.paywith.pw.data.repository.user.userInfo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.admin.AdminRole;
import kr.co.paywith.pw.data.repository.enumeration.AuthCd;
import kr.co.paywith.pw.data.repository.enumeration.CertTypeCd;
import kr.co.paywith.pw.data.repository.user.grade.Grade;
import kr.co.paywith.pw.data.repository.user.userApp.UserApp;
import kr.co.paywith.pw.data.repository.user.userStamp.UserStamp;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserInfo {

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
    @Column(length = 20)
    private String mobileNum;
    /**
     * 통신사 코드
     */
    @Column(length = 10)
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
    private Integer scorePlus = 0;
    /**
     * 현재 점수
     */
    private Integer scoreCnt = 0;

    /**
     * 등급 변동 일시
     */
    private ZonedDateTime gradeUpdtDttm;

    /** 회원 등급 ()*/
    @ManyToOne
    private Grade grade;

    // 승급관련 항목 end

    /**
     * 회원 누적 사용 점수
     */
    private Integer pointUsed = 0;
    /**
     * 회원 누적 적립 점수
     */
    private Integer pointChrged = 0;


    /**
     * 혜택을 받은 최종 등급
     *
     * 등급 업 후 등급 업 혜택을 받으면 해당 gradeSn으로 업데이트 한다(강등 후 재차 혜택 받는 것을 방지)
     */
    private Integer lastMaxGradeSn;

    /**
     * 활성 여부(실제 사용이 가능한 상태)
     */
    private Boolean activeFl = true;

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

    /**
     * 회원 앱 목록 ( 복수의 헨드폰 및
     */
    // kms: userApp Api 에서 CRUD 하도록 변경 예정
//    @OneToMany (cascade = CascadeType.ALL )
//    @OneToMany
//    private List<UserApp> userAppList;

//    /**
//     * 브랜드
//     */
//    @ManyToOne
//    private Brand brand;

    @OneToOne
    private UserStamp userStamp;

    /**
     * 회원 인증 키
     */
    // kms: 서버-클라이언트 통신 관련한 Authorization 과 헷갈릴 여지가 있어 cert로 이름 변경
    private String certKey;

    /**
     * 회원 인증 구분
     */
    // kms: 서버-클라이언트 통신 관련한 Authorization 과 헷갈릴 여지가 있어 cert로 이름 변경
    private CertTypeCd certTypeCd;


    /**
     * 원본 회원 아이디(기본 정보를 가지고 있을 회원 아이디) 일련번호
     */
    private Integer parentUserSn;

    /**
     * 테스터 여부1100000111
     * 클라이언트와 일부 서버의 기능을 선택적으로 사용가능한 회원인지 확인
     */
    private Boolean testerFl;


//    // kms: UserInfo 테이블에 있으면 관리권한
//    @NameDescription("권한 코드")
//    @Enumerated(EnumType.STRING)
//    @Column(length = 10)
//    private AuthCd authCd;

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
