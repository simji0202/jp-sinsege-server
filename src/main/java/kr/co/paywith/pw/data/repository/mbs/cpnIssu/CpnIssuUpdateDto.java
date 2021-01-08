package kr.co.paywith.pw.data.repository.mbs.cpnIssu;

import lombok.*;

import java.time.ZonedDateTime;

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

    // kms: 이미 표시된 쿠폰 정보 변경하지 않도록 함. 발급 오류의 경우 만료만 할 수 있도록 종료일시만 변경 가능
//    /**
//     * 쿠폰 표시(회원에게 노출) 일시
//     */
//    private ZonedDateTime showDttm = ZonedDateTime.now();
//
//    /**
//     * 쿠폰 유효 시작 일시
//     */
//    private ZonedDateTime validStartDttm = ZonedDateTime.now();

    /**
     * 쿠폰 유효 종료 일시
     */
    private ZonedDateTime validEndDttm = ZonedDateTime.now().plusDays(30);

}
