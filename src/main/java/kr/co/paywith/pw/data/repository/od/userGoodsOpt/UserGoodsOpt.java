package kr.co.paywith.pw.data.repository.od.userGoodsOpt;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.od.goodsOpt.GoodsOpt;
import kr.co.paywith.pw.data.repository.od.userGoodsOptEtc.UserGoodsOptEtc;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
public class UserGoodsOpt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;


    @NotNull
    @Size(max = 30)
    private String userId;

    private Integer goodsOptCnt;


    @OneToMany
    private List<UserGoodsOptEtc> goodsOptEtcList;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private GoodsOpt goodsOpt;


    @CreationTimestamp
    private LocalDateTime regDttm;

    @UpdateTimestamp
    private ZonedDateTime updtDttm;

    /**
     * 추가한 관리자
     */
    private String createBy;

    /**
     * 변경한  관리자
     */
    private String updateBy;


}