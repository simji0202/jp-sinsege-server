package kr.co.paywith.pw.data.repository.od.userMrhst;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class UserMrhst {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;

    @Size(max = 30)
    private String userId;


    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;

    @ManyToOne(fetch = FetchType.EAGER)
    private Mrhst mrhst;

    @CreationTimestamp
    private LocalDateTime regDttm;

    /**
     * 수정 일시
     */
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