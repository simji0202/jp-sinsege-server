package kr.co.paywith.pw.data.repository.admin;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.AuthType;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NameDescription("관리자")
    private String adminId;

    @NameDescription("이름")
    private String adminNm;

    @NameDescription("비밀번호")
    @JsonIgnore
    private String adminPw;

    @NameDescription("이메일 주소")
    private String emailAddr;

    @NameDescription("휴대폰 번호")
    private String mobileNum;

    // TODO 인증 시, 혹은 인증 후 최초 정보 취득시에 갱신하도록 구현해야 함
    @NameDescription("최종 로그인 아이피")
    private String lastLoginIp;

    @NameDescription("권한 코드")
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private AuthType authType;

    /**
     * 관리 가능한 브랜드
     */
    @NameDescription("브랜드정보")
    @ManyToOne
    private Brand brand;

    @NameDescription("사용 여부")
    private Boolean activeFl = true;

    @NameDescription("최종 로그인 일시")
    private ZonedDateTime lastLoginDttm;

    @NameDescription("관리자타입")
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AdminRole> roles = new HashSet<>();


    // 공통 부분
    @NameDescription("수정 일시")
    @UpdateTimestamp
    private ZonedDateTime updtDttm;

    @NameDescription("갱신담당자")
    private String updateBy;

    @NameDescription("등록 일시")
    @CreationTimestamp
    private ZonedDateTime regDttm;

    @NameDescription("등록담당자")
    private String createBy;

    @NameDescription("변경내용")
    private String updateContent;

}
