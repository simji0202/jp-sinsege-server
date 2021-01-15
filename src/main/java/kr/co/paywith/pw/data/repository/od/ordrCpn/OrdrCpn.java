package kr.co.paywith.pw.data.repository.od.ordrCpn;

import javax.persistence.Id;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.ZonedDateTime;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class OrdrCpn { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	private Integer amt;

	private LocalDate payDttm;

	private String confmNo;

	/**
	 * 취소 거래에 대해 연동서비스에 취소 요청후 응답 받았는지 여부. 취소 거래지만 false 라면 다시 요청을 보내야 함
	 */
	private Boolean cancelFl = false;

	private String cpnNo;

	/**
	 * pw-server의 쿠폰 종류 일련번호. cpnMasterSn
	 */
	private Integer cpnMasterSn;

}