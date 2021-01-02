package kr.co.paywith.pw.data.repository.mbs.cpnIssu;

import javax.persistence.ManyToOne;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpnMaster.CpnMaster;
import kr.co.paywith.pw.data.repository.mbs.cpnRule.CpnRule;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * 쿠폰 발급(대장)
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class CpnIssuUpdateDto {

    /**
     * 쿠폰 발급 명
     */
    private String cpnIssuNm;

    /**
     * 쿠폰 표시(회원에게 노출) 일시
     */
    private ZonedDateTime showDttm = ZonedDateTime.now();

    /**
     * 쿠폰 유효 시작 일시
     */
    private ZonedDateTime validStartDttm = ZonedDateTime.now();

    /**
     * 쿠폰 유효 종료 일시
     */
    private ZonedDateTime validEndDttm = ZonedDateTime.now().plusDays(30);

}
