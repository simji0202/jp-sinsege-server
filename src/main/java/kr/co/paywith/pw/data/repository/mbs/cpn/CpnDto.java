package kr.co.paywith.pw.data.repository.mbs.cpn;

import kr.co.paywith.pw.data.repository.mbs.enumeration.CpnSttsCd;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * 가맹점
 */
@Data
public class CpnDto {


    /**
     * 쿠폰 일련번호
     */
    private Integer id;

    /**
     * 쿠폰 번호
     */
    private String cpnNo;

    /**
     * 쿠폰 상태 코드
     */
    @Enumerated(EnumType.STRING)
    private CpnSttsCd cpnSttsCd;

    /**
     * 쿠폰 종류
     */
//    @ManyToOne
//    private CpnMaster cpnMaster;

    /**
     * 쿠폰 종류 일련번호
     */
    private Integer cpnMasterSn;

    /**
     * 쿠폰 소지 회원
     */
    private UserInfo userInfo;


    /**
     * 쿠폰 발급(대장)
     */
//    @ManyToOne
//    private CpnIssu cpnIssu;

    /**
     * 확인 여부
     */
    private Boolean readFl = false;

}
