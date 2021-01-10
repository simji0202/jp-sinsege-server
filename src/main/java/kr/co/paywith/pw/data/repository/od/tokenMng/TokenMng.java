package kr.co.paywith.pw.data.repository.od.tokenMng;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
public class TokenMng {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;

    @NotNull
    @Size(max = 100)
    private String accessToken;

    private String tokenType;

    private Integer expiresIn;

    private LocalDateTime expiresDttm;

    private String scope;

    @ManyToOne
    private Brand brand;

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