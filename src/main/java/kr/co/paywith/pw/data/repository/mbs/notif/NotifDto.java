package kr.co.paywith.pw.data.repository.mbs.notif;

import java.util.ArrayList;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.NotifType;
import kr.co.paywith.pw.data.repository.mbs.notifMrhst.NotifMrhst;
import kr.co.paywith.pw.data.repository.mbs.notifUser.NotifUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifDto {

  /**
   * 푸시 메시지 구분
   */
  private NotifType notifType = NotifType.USER;

  /**
   * 발송 일시. 예약 발송에 사용. 현재 시간이 sendDttm 이후라면(=이미 발송했다면) 삭제 불가
   */
  private LocalDateTime sendDttm = LocalDateTime.now();

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
  private String notifData;

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

  private List<NotifUser> notifUserList = new ArrayList<NotifUser>();

  private List<NotifMrhst> notifMrhstList = new ArrayList<NotifMrhst>();

}
