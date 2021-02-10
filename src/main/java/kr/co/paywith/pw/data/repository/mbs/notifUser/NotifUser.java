package kr.co.paywith.pw.data.repository.mbs.notifUser;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.notif.Notif;
import kr.co.paywith.pw.data.repository.mbs.notif.NotifSerializer;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class NotifUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("식별번호")
  private Integer id;

//  /**
//   * 회원
//   */
//  @ManyToOne
//  private UserInfo userInfo;

  /**
   * 회원 식별번호.
   */
  private Integer userId;

  /**
   * 푸시 메시지
   */
  @ManyToOne
  @JsonSerialize(using = NotifSerializer.class)
  private Notif notif;

  /**
   * 전송 수. fcm 요청 시의 단말기 등록 & 푸시 체크 기기 대수
   */
  private int sendCnt = 0;

  /**
   * 전송 일시. FCM 에 요청한 일시. null : 미전송 상태
   */
  private LocalDateTime sendDttm = null;
}
