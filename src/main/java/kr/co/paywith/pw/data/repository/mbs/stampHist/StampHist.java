package kr.co.paywith.pw.data.repository.mbs.stampHist;

import com.opencsv.bean.CsvBindByName;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.StampHistTypeCd;
import kr.co.paywith.pw.data.repository.mbs.cpnIssu.CpnIssu;
import kr.co.paywith.pw.data.repository.mbs.delng.Delng;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;


/**
 * 스템프 생성 및 쿠폰전환시 이력
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class StampHist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;

    // kms: 브랜드별 멤버십 정보 분리하면 userInfo 대신 해당 entity 연결
//    /**
//     * 회원  ( 누구에게 발행했니 ? )
//     */

//    같은 로직 및 스케줄러 (정기쿠폰)
    @ManyToOne
    private UserInfo userInfo;
//    private UserCard userCard;

    /**
     * 스탬프 이력 구분 코드
     */
    @Enumerated(EnumType.STRING)
    private StampHistTypeCd stampHistTypeCd;

    /**
     * 처리 일시
     */
    private ZonedDateTime setleDttm;

    /**
     * 스탬프 개수. 사용되었다면 음수가 들어간다
     */
    private Integer cnt = 0;


     // che2 : Delng 객체에서 참조
//    /**
//     * 요청 단말기 번호
//     */
//    private String trmnlNo;

    /**
     * 단말기 영수증 번호
     */
    @CsvBindByName(column = "POSMessageNo")
    private String trmnlDelngNo;

    /**
     * 취소 일시
     */
    private ZonedDateTime cancelRegDttm;



    //////////////////// start ///////////////////
    // che2 : 1) 거래에 위해서 발생한 경우 Delng 객체에서 참조 ///////////
    //        2) 수동 처리 경우  mrhst 참조

    @OneToOne
    private Mrhst mrhst ;

    ///////////////////// end  /////////////////////


//    /**
//     * 등급. 통계위한 거래 당시 회원의 등급
//     */
//    @ManyToOne(optional = true, fetch = FetchType.LAZY)
//    private Grade grade;

    /**
     * 거래 이력
     * <p>
     * 스탬프 적립 시 / 스탬프 직접 사용 시 연결(이 둘은 useTypeCd로 구분한다).
     * 스탬프 직접 사용은, 사용하지 않을 예정(커피베이에서 사용한 방식)
     * 거래 저장 후, StampHist 저장
     */
    @OneToOne
    private Delng delng;

    /**
     * 쿠폰 발급.
     * stampHistTypeCd.CPN 일 때 연결.
     * CpnIssu.stampHist 와 직접적으로 연결되는 게 아니므로 주의.
     * CpnIssu 저장 후 StampHist 저장
     * <p>
     * ex> 스탬프가 사용되었는데, 언제 어떤 쿠폰이 발급되었는지 확인
     */
    @OneToOne
    private CpnIssu cpnIssu;

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
