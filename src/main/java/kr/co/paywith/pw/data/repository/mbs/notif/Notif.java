package kr.co.paywith.pw.data.repository.mbs.notif;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.enumeration.NotifTypeCd;
import kr.co.paywith.pw.data.repository.enumeration.SendSttsCd;
import kr.co.paywith.pw.data.repository.mbs.notifUser.NotifUser;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.ZonedDateTime;
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
	private NotifTypeCd notifTypeCd = NotifTypeCd.USER;

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
	 * 전송 일시
	 */
	private ZonedDateTime sendDttm = ZonedDateTime.now();

	/**
	 * 전송 상태
	 */
	@Enumerated(EnumType.STRING)
	private SendSttsCd sendSttsCd = SendSttsCd.RDY;

	/**
	 * 전송 수량
	 */
	private Integer sendCnt = 0;

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
	 *
	 * 내부자(테스터) 관련 설정이 없어 미사용
	 */
	private Boolean sandboxFl = false;

	/**
	 * 테스트 전송 여부(FCM에서 정상 응답 받는지 확인)
	 */
	private Boolean testFl = false;

	// kms:
	/**
	 * 발송 회원 일련번호 목록
	 */
	@Transient
	private List<Integer> userSnArr;


	@OneToMany (mappedBy = "notif")
	private List<NotifUser> notifUsers = new ArrayList<NotifUser>();

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

	@NameDescription("갱신담당자")
	private String updateBy;

	@NameDescription("등록담당자")
	private String createBy;

}
