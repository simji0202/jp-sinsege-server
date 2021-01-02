package kr.co.paywith.pw.data.repository.user.userStamp;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.ZonedDateTime;
import javax.persistence.Id;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class UserStamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("식별번호")
  private Integer id;

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
  private ZonedDateTime stampUpdtDttm;

  /**
   * 카카오 페이 멤버십 스탬프 번호
   */
  private String kakaoStampNo;

  /**
   * 회원 스탬프 번호 (바코드 16자리))
   */
  private String stampNo;

  /**
   * 스탬프 누적 획득 개수
   */
  private Integer stampTotalGet = 0;

}