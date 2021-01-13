package kr.co.paywith.pw.data.repository.od.timesale;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.mbs.goodsOpt.GoodsOpt;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Timesale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;

    private Integer timesaleAmt;

    /**
     * 할인율 표시를 위해 사용(실제 계산하지는 않는다)
     */
    private Float dcRatio;

    /**
     * 할인 마감일
     */
    private LocalDateTime endDttm;

    /**
     * 할인 수량
     */
    private Integer timesaleCnt = 0;

    /**
     * 정렬 순서
     */
    private Integer sort = 0;

    @Size(max = 30)
    private String regUserId;

    @ManyToOne
    private Brand brand;


    @ManyToOne
    private Mrhst mrhst;

    @ManyToOne
    private GoodsOpt goodsOpt;


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

}