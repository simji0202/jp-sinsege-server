package kr.co.paywith.pw.data.repository.user.userCard;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.PrpaySttsCd;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class UserCard {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("식별번호")
  private Integer id;

  /**
   * 회원 카드 번호. 바코드 생성, 연동 위한 16자리 숫자
   */
  private String cardNo;

  ///////////   스템프 관련 정보 start /////////////
  /**
   * 회원 소지 스탬프 갯수
   */
  private Integer stampCnt = 0;

  /**
   * 현재 스탬프 적립 시작 일시
   */
  private ZonedDateTime stampStartDttm;

  /**
   * 스탬프 갱신 일시
   */
  private ZonedDateTime stampUpdtDttm;

  /**
   * 스탬프 누적 획득 개수
   */
  private Integer stampTotalCnt = 0;

  ///////////   스템프 관련 정보 end  /////////////

  ///////////   선불카드 관련 정보 start /////////////

  /**
   * 선불금액 유효 일시. 충전일 기준으로 정책으로 정하는 만료 일시
   */
  private ZonedDateTime prpayValidDttm;

  ///////////   선불카드 관련 정보 end /////////////

  ///////////   포인트 관련 정보 start /////////////
  /**
   * 회원 포인트
   */
  private int pointAmt = 0;
  /**
   * 회원 누적 포인트
   */
  private int pointTotalAmt = 0;
  ///////////   포인트 관련 정보 end /////////////

  /**
   * PIN 번호
   */
  private String pinNum;

  /**
   * 선불카드 상태 코드
   */
  @Enumerated(EnumType.STRING)
  private PrpaySttsCd prpaySttsCd = PrpaySttsCd.AVAIL;

  /**
   * 선불 사용 가능 금액
   */
  private int prpayAmt = 0;

  /**
   * 선불 누적 금액
   */
  private int prpayTotalAmt = 0;

  /**
   * 선불카드 이미지 웹 경로
   */
  private String imgUrl;

  /**
   * 사용 여부. 번호 유출 등으로 급하게 정지 요청을 받는다면 false로 정지시키고 환불, 재발급 등의 후속 조치를 한다
   */
  private Boolean prpayActiveFl = true;

  /**
   * 정지 일시
   */
  private ZonedDateTime prpayStopDttm;

  /**
   * 유저에게 카드 발급 일시
   */
  private ZonedDateTime prpayRegistDttm;

  ///////////   선불 카드 관련 정보 start /////////////




}
