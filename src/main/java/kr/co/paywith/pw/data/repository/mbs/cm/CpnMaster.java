package kr.co.paywith.pw.data.repository.mbs.cm;

import java.time.LocalDateTime;
import javax.persistence.*;

import kr.co.paywith.pw.data.repository.enumeration.CpnMasterType;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 쿠폰 종류 ( 무료쿠폰, 할인쿠폰, 상품쿠폰, 금액 쿠폰  등 )
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class CpnMaster {

    /**
     * 쿠폰 종류 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 쿠폰 명
     */
    private String cpnNm;

    /**
     * 쿠폰 마스터 종류. 상품에 관계없이 사용할 수 있는 쿠폰과 특정 상품에 사용하는 쿠폰.
     *
     * 사용 이력 연결에서 위치가 다르기 때문에
     */
    @Enumerated(EnumType.STRING)
    private CpnMasterType cpnMasterType;

    /**
     * 쿠폰코드(POS연동)
     */
    private String cpnCd;

    /**
     * 쿠폰 내용(본문)
     */
    @Column
    @Lob
    private String cpnCn;

    /**
     * 쿠폰 이미지 웹 경로
     */
    private String imgUrl;


    /////// 1+ 1 쿠폰 관련 항목 start/////
    /**
     * 1+1 대상 상품 (적용상품)
     */
    private Integer goodsId;

    /////// 1+ 1 쿠폰 관련 항목 end/////


    /////// 금액 쿠폰 관련 항목 start/////

    /**
     * 금액 쿠폰 ( 금액 할인 적용)
     */
    private int cpnAmt;
    /////// 금액  쿠폰 관련 항목 end/////


    /////// 할인  쿠폰 관련 항목 start/////
    /**
     * 쿠폰 할인 비율. 0이상이면 비율로 할인 처리( 0 - 100 까지 )
     */
    private int cpnRatio = 0;

    /////// 할인  쿠폰 관련 항목 end/////


    /**
     * 쿠폰 발급 유효기간. 필수
     */
    private int validDay;

    /**
     * 쿠폰 최소 사용 기준 금액.
     * 설정 금액 이상 사용할 때에만 쿠폰 사용가능
     */
    private int minUseStdAmt = 0;

    /**
     * 쿠폰 금액 상한.
     * cpnMasterType.RATIO 일 때, 할인할 수 있는 최대 금액을 설정한다.
     * ex> 50% 쿠폰(cpnRatio=50)은 1000원 결제 시 500원을 할인하지만, 여러 결제를 대신하면서 100만원 결제 시 사용하면 50만원을 받을 수 있어 부정 사용이 가능하다
     */
    private int maxCpnAmt = 0;


    @ManyToOne
    private Brand brand;

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

    /**
     * 추가한 관리자
     * 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
     */
    private String createBy;

    /**
     * 변경한  관리자
     * 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
     */
    private String updateBy;

}
