package kr.co.paywith.pw.data.repository.mbs.cpnGoods;

import kr.co.paywith.pw.data.repository.mbs.cpnMaster.CpnMaster;
import kr.co.paywith.pw.data.repository.mbs.goods.Goods;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * 가맹점
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class CpnGoods {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * 대상 상품
     */
    @ManyToOne
    private Goods goods;

    /**
     * 쿠폰 종류
     */
    @ManyToOne
    private CpnMaster cpnMaster;

    /**
     * 등록 일시
     */
    @CreationTimestamp
    private ZonedDateTime regDttm;

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

}
