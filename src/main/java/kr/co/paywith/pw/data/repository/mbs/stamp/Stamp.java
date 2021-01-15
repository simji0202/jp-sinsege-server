package kr.co.paywith.pw.data.repository.mbs.stamp;

import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.StampSttsTypeCd;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.delngPayment.DelngPayment;
import kr.co.paywith.pw.data.repository.mbs.stampHist.StampHist;
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
	private DelngPayment delngPayment;

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