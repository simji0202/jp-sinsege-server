package kr.co.paywith.pw.data.repository.mbs.stamp;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.enumeration.StampSttsTypeCd;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.stampHist.StampHist;
import kr.co.paywith.pw.data.repository.mbs.use.Use;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.ZonedDateTime;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Stamp { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	/**
	 * 만료 일시
	 */
	private ZonedDateTime expiredDttm;

	/**
	 * 수정 일시
	 */
	private ZonedDateTime updtDttm;

	/**
	 * 스탬프 상태 코드
	 */
	private StampSttsTypeCd stampSttsTypeCd = StampSttsTypeCd.RSRV;
	/**
	 * 스탬프를 적립하여 발급한 쿠폰
	 */
	@ManyToOne
	private Cpn cpn;

	/**
	 * 스탬프를 사용했을 때 해당 사용 이력
	 */
	@ManyToOne
	private Use use;

	// stampHist.UserInfo
//	/**
//	 * 스탬프를 소지(획득) 한 회원
//	 */
//	@ManyToOne
//	private UserInfo userInfo;

	/**
	 * 현재 스탬프를 적립한 이력(이력의 일자를 참고하여 만료 로직 수행)
	 */
	@ManyToOne
	private StampHist stampHist;

}