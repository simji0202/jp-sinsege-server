package kr.co.paywith.pw.data.repository.mbs.cpnMaster;

import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.cpnMasterGoods.CpnMasterGoods;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 쿠폰 종류 ( 무료쿠폰, 활인쿠폰, 상품쿠폰, 금액 쿠폰  등 )
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
     * 쿠폰 코드 (POS연동)
     */
    private String cpnCd;

    /**
     * 카카오페이 멤버십 쿠폰 고유 아이디(카카오에서 채번 후 전달)
     */
    //  @Column(length = 12, table = "CPN_MASTER_EXT")
    private Integer kakaoCouponId;
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

    /**
     * 쿠폰 대상 상품 목록
     */
    @OneToMany(mappedBy = "cpnMaster", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CpnMasterGoods> cpnMasterGoodsList = new ArrayList<CpnMasterGoods>();

    /**
     * 쿠폰 금액. 비율과 같이 사용하면 최대 할인 금액. 실제 상품 금액보다 작다면 이 금액만큼만 할인한다.
     */
    private Integer cpnAmt;

    /**
     * 쿠폰 할인 비율. 0이상이면 비율로 할인 처리(1이면 전액)
     */
    private Float cpnRatio = 0f;

    /**
     * 쿠폰 발급 제한 최대 수량
     */
    private Integer issuMaxCnt;

    /**
     * 쿠폰 발급 유효기간
     */
    private Integer validDay;

    /**
     * 쿠폰 최소 사용 기준 금액.
     * 설정 금액 이상 사용할 때에만 쿠폰 사용가능
     */
    private Integer minUseStdAmt;

    @ManyToOne
    private Brand brand;

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


    public void setCpnMasterGoodsList(List<CpnMasterGoods> cpnMasterGoodsList) {

        if (cpnMasterGoodsList != null) {
            this.cpnMasterGoodsList.clear();
            this.cpnMasterGoodsList.addAll(cpnMasterGoodsList);

        } else {
            this.cpnMasterGoodsList = cpnMasterGoodsList;
        }
    }
}
