package kr.co.paywith.pw.data.repository.mbs.useDetail;

import kr.co.paywith.pw.data.repository.mbs.goods.Goods;
import kr.co.paywith.pw.data.repository.mbs.useReq.UseReq;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 사용 상세
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UseDetail {

    /**
     * 사용 상세 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 사용 상세 개별 금액
     */
    private Integer useAmt;

    /**
     * 사용 상세 개별 상품
     */
    @ManyToOne
    private Goods goods;

    /**
     * 사용 상세 개별 상품 수량
     */
    @Max(value = 99)
    @Min(value = -99)
    private Integer goodsCnt;

    /**
     * 사용 이력
     */
    @ManyToOne
    private UserInfo userInfo;


    /**
     * 사용 요청
     */
    @ManyToOne
    private UseReq useReq;


}
