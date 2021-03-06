package kr.co.paywith.pw.data.repository.mbs.notif;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.enumeration.NotifType;
import kr.co.paywith.pw.data.repository.mbs.notifMrhst.NotifMrhst;
import kr.co.paywith.pw.data.repository.mbs.notifUser.NotifUser;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@ToString(exclude = {"notifUsers"})
public class Notif {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("식별번호")
  private Integer id;

  /**
   * 푸시 메시지 구분
   */
  private NotifType notifType = NotifType.USER;

  /**
   * 발송 일시. 예약 발송에 사용
   */
  private LocalDateTime sendReqDttm;

  /**
   * 푸시 메시지 제목
   */
  private String notifSj;

  /**
   * 푸시 메시지 본문
   */
  private String notifCn;

  /**
   * 푸시 메시지 추가데이터(앱 내 로직에서 사용)
   */
  @Column(columnDefinition = "json")
  private String notifData;

  /**
   * 전송 수량
   */
  private int sendCnt = 0;

  /**
   * 표시할 이미지 웹 경로
   */
  private String imgUrl;

  /**
   * 광고 푸시 여부
   *
   * 광고 수신 허용한 사람에게 (광고) 문구 붙여 전송
   */
  private Boolean adsFl = false;

  /**
   * 내부자(테스터) 타겟팅 전송 여부
   */
  private Boolean toTesterFl = false;

  /**
   * 테스트 전송 여부(FCM에서 정상 응답 받는지 확인)
   */
  private Boolean fcmTestFl = false;

  @OneToMany(mappedBy = "notif", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<NotifUser> notifUserList = new ArrayList<NotifUser>();

  @OneToMany(mappedBy = "notif", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<NotifMrhst> notifMrhstList = new ArrayList<NotifMrhst>();

  /**
   * 등록 일시
   */
  @CreationTimestamp
  private LocalDateTime regDttm;

  /**
   * 수정 일시
   */
  @UpdateTimestamp
  private LocalDateTime updtDttm;

  @NameDescription("갱신담당자")
  private String updateBy;

  @NameDescription("등록담당자")
  private String createBy;

}
