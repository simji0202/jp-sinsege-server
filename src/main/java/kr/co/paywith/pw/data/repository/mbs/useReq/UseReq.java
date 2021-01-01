package kr.co.paywith.pw.data.repository.mbs.useReq;

import java.util.List;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl.MrhstTrmnl;
import kr.co.paywith.pw.data.repository.mbs.use.Use;
import kr.co.paywith.pw.data.repository.mbs.useDetail.UseDetail;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * 사용(결제) 요청
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UseReq {

    /**
     * 사용 요청 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 승인번호
     */
    private String confmNo;

    /**
     * 단말기 번호
     */
    private String trmnlNo;

    /**
     * 단말기 영수증 번호
     */
    private String trmnlDelngNo;

    /**
     * 취소 일시
     */
    private ZonedDateTime cancelRegDttm;


    /**
     * 회원
     */
    @ManyToOne
    private UserInfo userInfo;

    /**
     * 가맹점. 판매하는 매장
     */
    @ManyToOne
    private Mrhst mrhst;


    /**
     * 가맹점 단말기
     * <p>
     * 단말기에서 온 요청은 단말기 정보가 존재. 회원에게 결제 요청을 보낸다.
     */
    @ManyToOne
    private MrhstTrmnl mrhstTrmnl;


    /**
     * 사용 상세(물품) 목록
     */
    @OneToMany
    private List<UseDetail> useDetailList;

    /**
     * 사용 이력
     * <p>
     * 참조 여부로 처리 완료를 확인
     */
    @ManyToOne
    private Use use;


    /**
     * 등록 일시
     */
    @CreationTimestamp
    private ZonedDateTime regDttm;

    /**
     * 수정 일시
     */
    @UpdateTimestamp
    private ZonedDateTime updtDttm;

}
