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
@Data
public class UseReqDto {

    /**
     * 사용 요청 일련번호
     */
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
     * 가맹점 일련번호
     */
    @ManyToOne
    private Mrhst mrhst;

    //// Mrhst 객체가 이미 MrhstTrmnl를 포함하고 있는데 다시 정의할  필요가 있을지 ??
    // kms: Mrhst는 거래가 이뤄지는 매장. MrhstTrmnl는 메시지 발신한 단말기 기록. trmnlNo 로 대체가 가능한지 검토 필요
    /**
     * 가맹점 단말기
     * <p>
     * 단말기에서 온 요청은 단말기 정보가 존재. 회원에게 결제 요청을 보낸다.
     */
    @ManyToOne
    private MrhstTrmnl mrhstTrmnl;


    // USE 객체가 이미 UseDetail를 포함하고 있는데 다시 정의할  필요가 있을지 ??
    // kms: UseReq+UseDetail 을 먼저 저장하고, 회원이 결제를 하면 같은 정보로 Use+UseDetail 생성
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


}
