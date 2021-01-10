package kr.co.paywith.pw.data.repository.od.mrhstoff;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.enumeration.OrdrType;
import kr.co.paywith.pw.data.repository.enumeration.PosTypeCd;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Mrhstoff { 


	///
	///   엔티티가 필요할지 확인

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;

	@Size(max = 100)
	private String mrhstNm;

	@Size(max = 1000)
	private String mrhstCn;

	private Boolean ordrFl;

	private Integer delivStdAmt;

	private Integer delivAmt;

	private Integer ordrMaxCnt;

	private Boolean openFl;

	private Integer rsrvDefaultAmt;

	private Integer rsrvMaxDay;

	private Integer rsrvUseTime;

	@ManyToOne
	private Brand brand;

	@Column(length = 300)
	private String address;

	@Column(length = 30)
	private String tel;

	/**
	 * POS 연계 등에 사용하는 식별코드(okpos의 shopCd 등)
	 */
	private String mrhstCd;

	private String corpNo;

	/**
	 * POS 타입
	 */
	@Enumerated(EnumType.STRING)
	private PosTypeCd posTypeCd;

	@Length(min = 4, max = 4)
	private String ordrTm1s;

	@Length(min = 4, max = 4)
	private String ordrTm1c;

	@Length(min = 4, max = 4)
	private String ordrTm2s;

	@Length(min = 4, max = 4)
	private String ordrTm2c;

	@Length(min = 4, max = 4)
	private String ordrTm3s;

	@Length(min = 4, max = 4)
	private String ordrTm3c;

	@Length(min = 4, max = 4)
	private String ordrTm4s;

	@Length(min = 4, max = 4)
	private String ordrTm4c;

	@Length(min = 4, max = 4)
	private String ordrTm5s;

	@Length(min = 4, max = 4)
	private String ordrTm5c;

	@Length(min = 4, max = 4)
	private String ordrTm6s;

	@Length(min = 4, max = 4)
	private String ordrTm6c;

	@Length(min = 4, max = 4)
	private String ordrTm7s;

	@Length(min = 4, max = 4)
	private String ordrTm7c;

	@Column(length = 10)
	@ElementCollection(fetch = FetchType.LAZY)
	@Enumerated(EnumType.STRING)
	private List<OrdrType> availOrdrTypeCdList;



	@CreationTimestamp
	private LocalDateTime regDttm;

	@UpdateTimestamp
	private LocalDateTime updtDttm;


	/**
	 * 추가한 관리자
	 */
	private String createBy;

	/**
	 * 변경한  관리자
	 */
	private String updateBy;


	@Transient
	private LocalDateTime now = LocalDateTime.now();

	@Transient
	public Boolean getOrdrAvailFl() {
		if (this.getOrdrFl() == false) {
			return false;
		}
		String startTm, endTm;
		switch (now.getDayOfWeek()) {
			case MONDAY:
				if (this.getOrdrTm1s() == null || this.getOrdrTm1c() == null) {
					return false;
				} else {
					startTm = this.getOrdrTm1s();
					endTm = this.getOrdrTm1c();
				}
				break;
			case TUESDAY:
				if (this.getOrdrTm2s() == null || this.getOrdrTm2c() == null) {
					return false;
				} else {
					startTm = this.getOrdrTm2s();
					endTm = this.getOrdrTm2c();
				}
				break;
			case WEDNESDAY:
				if (this.getOrdrTm3s() == null || this.getOrdrTm3c() == null) {
					return false;
				} else {
					startTm = this.getOrdrTm3s();
					endTm = this.getOrdrTm3c();
				}
				break;
			case THURSDAY:
				if (this.getOrdrTm4s() == null || this.getOrdrTm4c() == null) {
					return false;
				} else {
					startTm = this.getOrdrTm4s();
					endTm = this.getOrdrTm4c();
				}
				break;
			case FRIDAY:
				if (this.getOrdrTm5s() == null || this.getOrdrTm5c() == null) {
					return false;
				} else {
					startTm = this.getOrdrTm5s();
					endTm = this.getOrdrTm5c();
				}
				break;
			case SATURDAY:
				if (this.getOrdrTm6s() == null || this.getOrdrTm6c() == null) {
					return false;
				} else {
					startTm = this.getOrdrTm6s();
					endTm = this.getOrdrTm6c();
				}
				break;
			case SUNDAY:
				if (this.getOrdrTm7s() == null || this.getOrdrTm7c() == null) {
					return false;
				} else {
					startTm = this.getOrdrTm7s();
					endTm = this.getOrdrTm7c();
				}
				break;
			default:
				return false;
		}
		try {
			LocalTime startTime = LocalTime.now().withHour(Integer.parseInt(startTm.substring(0, 2)))
					.withMinute(Integer.parseInt(startTm.substring(2, 4)));
			LocalTime endTime = LocalTime.now().withHour(Integer.parseInt(endTm.substring(0, 2)))
					.withMinute(Integer.parseInt(endTm.substring(2, 4)));
			return now.toLocalTime().isAfter(startTime) && now.toLocalTime().isBefore(endTime);
		} catch(java.time.DateTimeException e) {
			return false;
		}
	}

}