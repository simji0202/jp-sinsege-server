package kr.co.paywith.pw.data.repository.mbs.cpnIssuRule;

import kr.co.paywith.pw.data.repository.mbs.cm.CpnMaster;
import kr.co.paywith.pw.data.repository.enumeration.CpnIssuRuleType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * 가맹점
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class CpnIssuRuleUpdateDto {

    /**
     * 쿠폰 발급 규칙 코드
     */
    @Enumerated(EnumType.STRING)
    private CpnIssuRuleType cpnIssuRuleType;
    /**
     * 쿠폰 발급 규칙 명
     */
    private String ruleNm;
    /**
     * 쿠폰 발급 규칙 기준 값
     *
     * cpnIssuRuleType에 따라 다르게 사용
     *
     * AC, AU, C, U : 기준 금액
     *
     * J, BD, S, GU : 대상회원에게 발급할 시간(0~23)
     */
    private Integer stdValue;

    /**
     * 쿠폰 발급 시 메시지 전송 여부
     */
    private Boolean msgSendFl = false;

    /**
     * 쿠폰 발급 전송 메시지 제목
     */
    private String msgSj;

    /**
     * 이메일, 푸시 등 발급시 전송할 메시지 본문
     */
    private String msgCn;

    /**
     * 발급할 쿠폰 종류
     */
    private CpnMaster cpnMaster;

    /**
     * 발급 후 메시지 전송 지연 시간
     */
    private Integer msgDelayHr = 0;

    /**
     * 활성 여부
     */
    private Boolean activeFl;

    /**
     * 발급 규칙 매칭 월
     */
    private Integer ruleMonth;
    /**
     * 발급 규칙 매칭 일
     */
    private Integer ruleDay;
    /**
     * 발급 규칙 매칭 주
     */
    private Integer ruleWeekOfMonth;
    /**
     * 발급 규칙 매칭 요일
     */
    private Integer ruleDayOfWeek;
    /**
     * 발급 규칙 매칭 시간
     */
    private Integer ruleHour;

    /**
     * 발급 규칙 매칭 분
     */
    private Integer ruleMinute;
}
