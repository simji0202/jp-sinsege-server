package kr.co.paywith.pw.data.repository.mbs.brandPg;

import kr.co.paywith.pw.data.repository.enumeration.PgTypeCd;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 브랜드에서 사용하는 PG
 */
// kms: pw-proxy 의 pg 정보를 대신 사용 예정
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class BrandPg implements Serializable {

    private static final long serialVersionUID = 1380502539528568057L;

    /**
     * PG 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * PG코드
     * <p>
     * api : mallId 등
     * <p>
     * pw-proxy : mallId를 등록 후 발급 받는 pgId
     */
    private String pgCd;

    /**
     * pg암호
     * <p>
     * pw-proxy에서 결제확인, 취소 시에 사용하는 암호(pwPw)
     */
    private String pgKey;

    // kms: 유효성을 위해 pgTypeCd로 대체한 필드
//    /**
//     * PG 이름
//     * <p>
//     * API : 경로로도 사용하는 영문 pg 약어(kicc)
//     * <p>
//     * pw-proxy : PG 종류 식별을 위해 사용(KICC / NICE)
//     */
//    private String pgNm;

    /**
     * PG 구분. pw-server 내에서 PG 구분을 위해 사용하고 pw-proxy 에 식별 위해 전달(KICC / NICE)
     */
    @Enumerated(EnumType.STRING)
    private PgTypeCd pgTypeCd;

    /**
     * 등록일시
     */
    @CreationTimestamp
    private ZonedDateTime regDttm;

    /**
     * 수정일
     */
    @UpdateTimestamp
    private ZonedDateTime updtDttm;
}
