package kr.co.paywith.pw.data.repository.mbs.cpnMasterGoods;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.paywith.pw.data.repository.mbs.cpnMaster.CpnMaster;
import kr.co.paywith.pw.data.repository.mbs.cpnMaster.CpnMasterSerializer;
import kr.co.paywith.pw.data.repository.mbs.goods.Goods;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 *
 * CpnMasterGoods 을 이용하여 , 쿠폰마스터 및 상품 등록을 일괄적으로 처리
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class CpnMasterGoods {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 대상 상품 (적용상품)
     */
    @OneToOne
    private Goods goods;

    @ManyToOne
    @JsonSerialize(using = CpnMasterSerializer.class)
    private CpnMaster cpnMaster;

    /**
     * 상품 개수
     */
    private Integer goodsCnt;

    /**
     * 상품 할인 적용 금액
     *
     * Ratio가 없으면 이 금액만큼 할인. 없으면 최대 할인 금액으로 사용한다
     */
    private Float goodsAmt;

    /**
     * 상품 할인 적용 금액 비율
     *
     * 0 이상이면 비율로 할인 처리. 값이 있을 경우 상품단가에 곱하여 계산하고 goodsAmt가 있다면 상한만큼까지만 할인
     */
    private Float goodsRatio = 0f;

    /**
     * 등록 일시
     */
    @CreationTimestamp
    private ZonedDateTime regDttm;


}
