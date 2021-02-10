package kr.co.paywith.pw.data.repository.user.user;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import kr.co.paywith.pw.data.repository.admin.AdminRole;
import kr.co.paywith.pw.data.repository.enumeration.CertType;
import kr.co.paywith.pw.data.repository.user.userCard.UserCard;
import lombok.Data;

/**
 * 회원 삭제 정보
 */
@Data
public class UserInfoDto {

  /**
   * 회원 아이디
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
  private int scorePlus = 0;
  /**
   * 현재 점수
   */
  private int scoreCnt = 0;

  /**
   * 등급 변동 일시
   */
  private LocalDateTime gradeUpdtDttm;

  /**
   * 회원 누적 사용 점수
   */
  private int pointUsed = 0;
  /**
   * 회원 누적 적립 점수
   */
  private int pointChrged = 0;

  /**
   * 활성 여부(실제 사용이 가능한 상태)
   */
  private Boolean activeFl = true;

  /**
   * 최근 로그인 일시
   */
  private LocalDateTime lastLoginDttm;


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
  private LocalDateTime outDttm;


  private UserCard userCard;

  /**
   * 회원 인증 키
   */
  // kms: 서버-클라이언트 통신 관련한 Authorization 과 헷갈릴 여지가 있어 cert로 이름 변경
  private String certKey;

  /**
   * 회원 인증 구분
   */
  // kms: 서버-클라이언트 통신 관련한 Authorization 과 헷갈릴 여지가 있어 cert로 이름 변경
  private CertType certType;


  /**
   * 테스터 여부1100000111 클라이언트와 일부 서버의 기능을 선택적으로 사용가능한 회원인지 확인
   */
  private Boolean testerFl;

  // kms: 일반 회원도 rule 필요한 지
  /**
   * 관리자타입
   */
  @Enumerated(EnumType.STRING)
  private Set<AdminRole> roles;

}
