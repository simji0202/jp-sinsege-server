package kr.co.paywith.pw.data.repository.mbs.billing;

import kr.co.paywith.pw.data.repository.enumeration.PwCorpType;
import kr.co.paywith.pw.data.repository.prx.brandPg.BrandPg;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 빌링(정기결제 정보)
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Billing {

    /**
     * 빌링 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 빌키(정기결제에서 카드번호 대용)
     */
    private String billKey;

    /**
     * 빌키 별명
     */
    @Column(length = 50)
    private String billNm;

    /**
     * 빌키 암호(Optional). 서버에서 검증하는 방식으로 사용하며 보안을 위해 클라이언트에 응답하지 않음
     */
    @Column(length = 10)
    @JsonIgnore
    private String billPw;

    /**
     * 카드사 명
     */
    @Column
    private String corpNm;

    /**
     * 카드사 코드
     */
    @Column(length = 10)
    private PwCorpType corpCd;

    /**
     * 카드 번호
     */
    @Column(length = 20)
    private String cardNo;

    /**
     * 원본 업체 코드
     */
    @Column(length = 20)
    private String insttCd;

    /**
     * 회원
     */
    @ManyToOne
    private UserInfo userInfo;

    /**
     * 브랜드PG
     */
    // kms: 복수의 pg를 사용할 수 있으므로 Brand 관계된 PG 정보 필요
    // kms: pw-proxy 의 pg 정보 연결
    @ManyToOne
    private BrandPg brandPg;


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
}
