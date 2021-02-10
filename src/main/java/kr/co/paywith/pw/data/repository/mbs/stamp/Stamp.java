package kr.co.paywith.pw.data.repository.mbs.stamp;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.StampSttsType;
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

	/**
	 *

	 UserCard
	    회원 스탬프 갯수    2
	    현재 스탬프 적립 시작 일시
	    스탬프 적립 시작 일시
	    스탬프 갱신 일시
	    회원 스탬프 번호
	    스탬프 누적 획득 갯수 <--- 거래취소 이외에 적립


	 Stamp   --> 스탬프 직인 하나당 ,   레코드 하나씩 추가 ...  (사용한쿠폰만 정산하고 싶을때 엔티티  ) 낙천 수입 배제 ..
	    수정 일시
	    스탬프 상태 코드
	    스탬프 적립하여 발급한 쿠폰
	    스탬프를 사용했을 때 해당 사용 이력
	    현재 스탬프를 적립한 이력(이력의 일자를 참고하여 만료 로직 수행)

	 StampHist

	     회원
	     스탬프 이력 구분 코드 ( 직접 적립, 적립, 사용, 쿠폰발급, 만료)
		 처리 일시
		 스탬프 개수. 사용되었다면 음수가 들어간다   3
	     쿠폰 발행의 사용된 스탬프 갯수 ??
		 요청 단말기 번호
		 단말기 영수증 번호  trmnlDelngNo
		 취소 일시
		 가맹점           Mrhst
		 거래 이력         Delng
		 쿠폰 발급.        cpnIssu
		 등록 일시
		 수정 일시
	     만료 일시


	 거래 일시 1일  30일

1	   dsklsfda  3  3
2	   dsfsfsd   4  4
3	   sfsdfs    5  3
	   발급       -10   (2)
10    	 sfsdfs    5  3
30    	 sfsdfs    5  3
1    	 sfsdfs    5  3



	 1) 거래 발생할 경우
	   거래별 적립 ??  상품별  적립  ?  금액별 ?


	 2) 거래가 발생 후, 스탬프 발행 ( 스탬프 발행으로 문제가 발생할 경우 트렌잭션은 ?? )
	  PG ?


	 3) 스템프 사용은 ? ( 커피베이 )   (x)
	    쿠폰을 통해서 ? ( 페이위드 전략 )
	    스탬프로 인해 쿠폰 발행 후 ,  스탬프 취소 발생  ?


	 4) 쿠폰 정산에 대해서 ..???

	 예) 상품별 ....
	  # 결재 거래 완료 ( 취소 )

	   StampHist 생성
	        스탬프이력구분코드 = 적립
	        가맹점         =  영등포구점
	        거래이력        =  delng


	   UserCard.회원스탬프갯수  + 1


	  # 스탬프 10개 적립 후, 쿠폰 발행  ( 취소 )
       -> 브랜드 설정 정보 항목 stampMaxCnt

	 StampHist 생성
	 스탬프이력구분코드 = 쿠폰발급
	 쿠폰           =  cpnIssu
	                 delng



	    거래랑 상관없이 ....
	    쿠폰 발급     ( 갯수 )
	       1---      8
	       3--- 쿠폰  11  (+2)


	       ????????


	   개별적 사용 추적 ... ?? 정산 ..


	 */



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NameDescription("식별번호")
	private Integer id;



	/**
	 * 스탬프 상태 코드
	 */
	private StampSttsType stampSttsType = StampSttsType.RSRV;
//
	/**
	 * 스탬프를 적립하여 발급한 쿠폰
	 */
	// @ManyToOne
	private Integer cpnId;

//	/**
//	 * 스탬프를 사용했을 때 해당 사용 이력
//	 */
//	@ManyToOne
//	private DelngPayment delngPayment;

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

	/**
	 * ( 브랜드 설정에 stampValidPeriod 항목을 기준으로 만료 일시 설정 )
	 * 만료 일시
	 */
	private LocalDateTime expiredDttm;

	/**
	 * 수정 일시
	 */
	private LocalDateTime updtDttm;

}
