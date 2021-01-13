package kr.co.paywith.pw.data.repository.od.ordrHist;

import javax.persistence.Id;

import kr.co.paywith.pw.data.repository.enumeration.OrdrStatus;
import kr.co.paywith.pw.data.repository.enumeration.OrdrType;
import kr.co.paywith.pw.data.repository.enumeration.ReserveOrdrType;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.od.ordrCancel.OrdrCancel;
import kr.co.paywith.pw.data.repository.od.ordrCpn.OrdrCpn;
import kr.co.paywith.pw.data.repository.od.ordrDeliv.OrdrDeliv;
import kr.co.paywith.pw.data.repository.od.ordrGoodsOpt.OrdrGoodsOpt;
import kr.co.paywith.pw.data.repository.od.ordrGoodsOptEtc.OrdrGoodsOptEtc;
import kr.co.paywith.pw.data.repository.od.ordrHistReview.OrdrHistReview;
import kr.co.paywith.pw.data.repository.od.ordrPay.OrdrPay;
import kr.co.paywith.pw.data.repository.od.ordrSender.OrdrSender;
import kr.co.paywith.pw.data.repository.od.statProc.StatProc;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import kr.co.paywith.pw.common.NameDescription;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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
public class OrdrHist { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;


	@Enumerated(EnumType.STRING)
	private OrdrStatus ordrStatCd;

	private Integer ordrTotAmt;

	@Enumerated(EnumType.STRING)
	private OrdrType ordrTypeCd;

	@Size(max = 1000)
	private String ordrReqCn;

	@Enumerated(EnumType.STRING)
	private ReserveOrdrType rsrvOrdrTypeCd;

	private LocalDateTime rsrvOrdrDttm;

	/**
	 * <del>선불 서비스 회원일경우 userSn, 아닐 경우 mobileNum 사용</del>
	 *
	 * 멤버십 서버의 userSn. 회원으로 가입하지 않았어도 user 테이블 정보를 생성하므로 userSn을 그대로 넣는다.
	 */
	@NotNull
	@Size(max = 30)
	private String ordrUserId;

	@Size(max = 30)
	private String ordrUserNm;

	@Size(max = 15)
	private String ordrUserMobile;

	@Size(max = 10)
	private String mobileRegNo;

	@CreationTimestamp
	private LocalDateTime ordrDttm;

	/**
	 * 예상 완료(배달 완료, 혹은 매장 서빙 완료) 일시
	 */
	private LocalDateTime expectCompDttm;

	/**
	 * 결제 관련 내용 변경 금지 여부. 결제 금액이 맞지 않도록 수정하는 걸 방지한다
	 */
	private Boolean prohibitChangePayFl = false;

	/**
	 * 접수 일시(매장에서 상태변경을 처음 한 일시
	 */
	private LocalDateTime acceptDttm;

	/**
	 * 결제 완료 여부.
	 *
	 * 매번 계산하는 걸 막기 위해 결제가 완료(상품금액 = 결제금액+쿠폰금액)되면 true, 상품이 추가되는 등 불일치되면 다시 False로 설정
	 */
	private Boolean payCompFl = false;

	/**
	 * 정산처리(현재는 PW_PAY 일때만 사용) 기준이 되는 거래 완료 일시.
	 *
	 * 완료 전 취소를 하면 바로 환불
	 */
	private LocalDateTime compDttm;

	@OneToMany
	private List<OrdrGoodsOpt> goodsOptList;

	@OneToMany
	private List<OrdrPay> payList;

	@OneToOne
	private OrdrDeliv ordrDeliv;

	@OneToOne
	private OrdrSender ordrSender;

	@ManyToOne(fetch = FetchType.LAZY)
	private Brand brand;


	@ManyToOne
	private Mrhst mrhst;

	@ManyToOne(fetch = FetchType.LAZY)
	private StatProc statProc;

	private Integer score;

	@OneToMany
	private List<OrdrHistReview> ordrHistReviewList;

	@OneToOne
	private OrdrCpn ordrCpn;

	@OneToOne
	private OrdrCancel ordrCancel;

	@Transient
	private boolean posRequest;

	@Transient
	public String getOrdrNm() {
		String nm = "";
		if (this.goodsOptList != null && this.goodsOptList.size() > 0) {
	//		nm += this.goodsOptList.get(0).getGoodsOpt().getGoodsOptNm();
			if (this.goodsOptList.size() > 1) {
				nm += "외 " + (this.goodsOptList.size() - 1) + " 상품";
			}
		} else {
			nm += "상품";
		}
		return nm;
	}
//
//	/**
//	 * 결제 완료여부를 확인하기 위해 현재 결제 금액 계산
//	 *
//	 * @return 쿠폰 포인트 등을 포함한 결제 금액 합
//	 */
//	@Transient
//	public int getPayAmt() {
//		int result = 0;
//		if (this.payList != null) {
//			for (OrdrPay ordrPay : this.payList) {
//				result += ordrPay.getPayAmt();
//			}
//		}
//
//		if (this.ordrCpn != null) {
//			// 금액 쿠폰
//			result += this.ordrCpn.getAmt();
//		}
//
//		if (this.goodsOptList != null) {
//			for (OrdrGoodsOpt ordrGoodsOpt : this.goodsOptList) {
//				if (ordrGoodsOpt.getOrdrGoodsCpn() != null) {
//					result += ordrGoodsOpt.getOrdrGoodsCpn().getAmt();
//				}
//
//				if (ordrGoodsOpt.getGoodsOptEtcList() != null) {
//					for (OrdrGoodsOptEtc ordrGoodsOptEtc : ordrGoodsOpt.getGoodsOptEtcList()) {
//						if (ordrGoodsOptEtc.getOrdrOptEtcCpn() != null) {
//							result += ordrGoodsOptEtc.getOrdrOptEtcCpn().getAmt();
//						}
//					}
//				}
//			}
//		}
//		return result;
//	}


}