package kr.co.paywith.pw.data.repository.mbs.mrhstOrdr;

import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class MrhstOrdr {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("일련번호")
  private Integer id;

  /**
   * 영업시간을 예약단위로 나누어서 예약할 수 있게 한다.
   *
   * 예약단위 분이 120이면 한번 예약에 120분 사용.
   */
  @NameDescription("예약 단위 분")
  private Integer rsrvUnitMin;

  /**
   * 야간 운영 : 오후에 오픈해서 <b>0시를 지나</b> 새벽이나 아침에 닫는 경우
   */
  @NameDescription("야간운영_여부")
  private Boolean nightOpenFl = false;

  /**
   * 주문 여부. 오픈 시간이며, 주문 여부가 true 인 매장에서 주문 가능
   */
  @NameDescription("주문 여부")
  private Boolean ordrFl = false;

  /**
   * 배달 여부. true 인 매장만 배달 가능
   */
  @NameDescription("배달 여부")
  private Boolean delivFl = false;

  /**
   * 배달 금액. 지역 설정 따로 하지 않으면 이 금액을 배달 비용으로 가산
   */
  @NameDescription("배달 금액")
  private Integer delivAmt;

  /**
   * 시간표 사용 여부. 스케쥴러로 매일 설정한 일자의 timetable 을 생성한다
   */
  @NameDescription("시간표 여부")
  private Boolean useTimetableFl = false;

  @NameDescription("시작시간_월")
  private LocalTime openTm1;

  @NameDescription("시작시간_화")
  private LocalTime openTm2;

  @NameDescription("시작시간_수")
  private LocalTime openTm3;

  @NameDescription("시작시간_목")
  private LocalTime openTm4;

  @NameDescription("시작시간_금")
  private LocalTime openTm5;

  @NameDescription("시작시간_토")
  private LocalTime openTm6;

  @NameDescription("시작시간_일")
  private LocalTime openTm7;

  @NameDescription("종료시간_월")
  private LocalTime closeTm1;

  @NameDescription("종료시간_화")
  private LocalTime closeTm2;

  @NameDescription("종료시간_수")
  private LocalTime closeTm3;

  @NameDescription("종료시간_목")
  private LocalTime closeTm4;

  @NameDescription("종료시간_금")
  private LocalTime closeTm5;

  @NameDescription("종료시간_토")
  private LocalTime closeTm6;

  @NameDescription("종료시간_일")
  private LocalTime closeTm7;

  @NameDescription("리뷰_점수")
  private float reviewScore = 0;

  @NameDescription("리뷰_0_개수")
  private int review0Cnt;

  @NameDescription("리뷰_1_개수")
  private int review1Cnt;

  @NameDescription("리뷰_2_개수")
  private int review2Cnt;

  @NameDescription("리뷰_3_개수")
  private int review3Cnt;

  @NameDescription("리뷰_4_개수")
  private int review4Cnt;

  @NameDescription("리뷰_5_개수")
  private int review5Cnt;

}
