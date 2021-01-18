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
   * 회원 스탬프 번호 (바코드 16자리))
   */
  private String stampNo;

  /**
   * 스탬프 누적 획득 개수
   */
  private Integer stampTotalGet = 0;

  ///////////   스템프 관련 정보 end  /////////////

  /**
   * 선불카드 번호
   */
  private String prpayNo;

  /**
   * 선불카드 이름
   */
  private String prpayNm;


  /**
   * PIN 번호
   */
  private String pinNum;


  /**
   * 최초 사용 일시
   */
  private ZonedDateTime prpayUseDttm;

  /**
   * 유효 일시
   */
  private ZonedDateTime prpayValidDttm;

  /**
   * 선불카드 상태 코드
   */
  @Enumerated(EnumType.STRING)
  private PrpaySttsCd prpaySttsCd = PrpaySttsCd.AVAIL;

  /**
   * 사용 가능 금액
   */
  private Integer prpayUsePosblAmt = 0;

  /**
   * 최대 충전 가능 금액
   */
  private Integer prpayChrgMaxAmt = 99999999;

  /**
   * 총 충전 금액
   */
  private Integer prpayChrgTotAmt = 0;

  /**
   * 총 사용 금액
   */
  private Integer prpayUseTotAmt = 0;

  /**
   * 선불카드 이미지 웹 경로
   */
  private String imgUrl;


  /**
   * 사용 여부
   */
  private Boolean prpayActiveFl;


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
