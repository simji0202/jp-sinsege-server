package kr.co.paywith.pw.data.repository.mbs.goodsGrp;

import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 상품 그룹(카테고리)
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class GoodsGrp {

    /**
     * 상품 그룹 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 상품 그룹 부모 일련번호
     */
    private Integer parentId;

    /**
     * 상품 그룹 명
     */
    private String goodsGrpNm;

    /**
     * 상품 그룹 코드
     */
    private String goodsGrpCd;

    /**
     * 정렬순서
     */
    private Integer sort = 0;

    /**
     * 사용 여부
     */
    private Boolean activeFl = false;

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
     */
    private String createBy;

    /**
     * 변경한  관리자
     */
    private String updateBy;

}
