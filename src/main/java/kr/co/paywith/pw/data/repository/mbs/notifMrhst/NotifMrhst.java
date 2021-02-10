package kr.co.paywith.pw.data.repository.mbs.notifMrhst;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.mbs.cpnIssu.CpnIssuSerializer;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.mbs.notif.Notif;
import kr.co.paywith.pw.data.repository.mbs.notif.NotifSerializer;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class NotifMrhst {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("식별번호")
  private Integer id;

//  /**
//   * 매장
//   */
//  @ManyToOne
//  private Mrhst mrhst;

  /**
   * 매장 식별번호.
   */
  private Integer mrhstId;

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
   * 전송 일시
   */
  private LocalDateTime sendDttm;
}
