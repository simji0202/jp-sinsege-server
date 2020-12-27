package kr.co.paywith.pw.data.repository.mbs.questCpnRule;

import lombok.Data;

@Data
public class QuestCpnRuleUpdateDto {

    /**
     * 퀘스트 쿠폰 규칙 이름
     */
    private String questCpnRuleNm;

    /**
     * 최대 달성 횟수
     */
    private Integer maxCnt; // 최대 달성 횟수


}