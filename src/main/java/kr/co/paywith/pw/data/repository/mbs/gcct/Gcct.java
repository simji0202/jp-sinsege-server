package kr.co.paywith.pw.data.repository.mbs.gcct;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.goods.Goods;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 상품권. 취소하면 환불해준다는 점에서 쿠폰과 다름.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Gcct {

    /**
     * 상품권 일련번호 (KEY)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 유효일시. 환결설정값을 받아, 등록일시 기준 +해서 set
     */
    private LocalDateTime validEndDttm;

    /**
     * 상품권 번호
     */
    private String gcctNo;

    /**
     * 상품권 명. 구매 당시 MobileGoods.mobileGoodsNm
     */
    private String gcctNm;

    /**
     * 상품
     */
    @ManyToOne
    private Goods goods;

    /**
     * 보낸 회원.
     * 유효기간 내 사용하지 않으면 보낸 회원에게 환불.
     * 보낸회원이 조회할 경우 사용방지 하기 위해 gcctNo 숨겨야 한다
     */
    @ManyToOne
    private UserInfo fromUserInfo;

    /**
     * 받은 회원.
     */
    @ManyToOne
    private UserInfo toUserInfo;

    /**
     * 메시지 제목
     */
    private String sj;

    /**
     * 메시지 본문
     */
    private String cn;

    /**
     * 확인 여부. 클라이언트에서 확인하지 않은 상품권 수 뱃지 표시 등에 사용
     */
    private Boolean readFl = false;

    /**
     * 취소 일시. null 여부로 취소 여부 확인
     */
    private LocalDateTime usedDttm;

    /**
     * 취소 일시. null 여부로 취소 여부 확인
     */
    private LocalDateTime cancelRegDttm;

    // kms: TODO prx 결제 만들어지면 여기 연결
//    /**
//     * 결제
//     */
//    @ManyToOne
//    private Pay pay;

    @NameDescription("변경 일시")
    @CreationTimestamp
    private LocalDateTime regDttm;

    @NameDescription("수정 일시")
    @UpdateTimestamp
    private LocalDateTime updtDttm;

    @NameDescription("갱신담당자")
    private String updateBy;

    @NameDescription("등록담당자")
    private String createBy;

    @NameDescription("취소담당자")
    private String cancelBy;
}
