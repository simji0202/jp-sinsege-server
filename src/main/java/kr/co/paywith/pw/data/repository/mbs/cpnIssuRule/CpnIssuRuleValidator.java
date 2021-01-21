package kr.co.paywith.pw.data.repository.mbs.cpnIssuRule;


import kr.co.paywith.pw.component.ValidatorUtils;
import kr.co.paywith.pw.data.repository.enumeration.CpnIssuRuleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CpnIssuRuleValidator {

    @Autowired
    private CpnIssuRuleRepository cpnIssuRuleRepository;


    public void validate(CpnIssuRuleDto cpnIssuRuleDto, Errors errors) {

        ValidatorUtils.checkString(cpnIssuRuleDto.getMsgSj(), "메시지 제목", errors, false, 1, 100);
        ValidatorUtils.checkString(cpnIssuRuleDto.getMsgCn(), "메시지 본문", errors, false, 1, 4000);
        ValidatorUtils.checkInteger(cpnIssuRuleDto.getRuleMonth(), "기준 월", errors, false, 1, 12);
        ValidatorUtils.checkInteger(cpnIssuRuleDto.getRuleDay(), "기준 일", errors, false, 1, 31);
        ValidatorUtils.checkInteger(cpnIssuRuleDto.getRuleDayOfWeek(), "기준 요일", errors, false, 1, 31);
        ValidatorUtils.checkInteger(cpnIssuRuleDto.getRuleHour(), "기준 시", errors, false, 0, 23);
        ValidatorUtils.checkInteger(cpnIssuRuleDto.getRuleMinute(), "기준 분", errors, false, 0, 59);
        ValidatorUtils.checkInteger(cpnIssuRuleDto.getMsgDelayHr(), "메시지 지연 시간", errors, false, 0, null);

        if (cpnIssuRuleDto.getCpnIssuRuleType().equals(CpnIssuRuleType.STAMP)) {
            // 스탬프 쿠폰 발급 규칙은 시스템 내 한개만 존재해야 함.
            // 발급 시 스탬프가 차감되므로, 여러 개의 규칙이 있으면 처리 어려움
            if (cpnIssuRuleRepository.countByCpnIssuRuleType(CpnIssuRuleType.STAMP) > 0) {
                errors.reject("규칙 중복 오류", "스탬프 규칙은 시스템 내 한개만 있어야 합니다");
            }
        }
        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

    public void validate(CpnIssuRuleUpdateDto cpnIssuRuleUpdateDto, CpnIssuRule cpnIssuRule, Errors errors) {

        ValidatorUtils.checkString(cpnIssuRuleUpdateDto.getMsgSj(), "메시지 제목", errors, false, 1, 100);
        ValidatorUtils.checkString(cpnIssuRuleUpdateDto.getMsgCn(), "메시지 본문", errors, false, 1, 4000);
        ValidatorUtils.checkInteger(cpnIssuRuleUpdateDto.getRuleMonth(), "기준 월", errors, false, 1, 12);
        ValidatorUtils.checkInteger(cpnIssuRuleUpdateDto.getRuleDay(), "기준 일", errors, false, 1, 31);
        ValidatorUtils.checkInteger(cpnIssuRuleUpdateDto.getRuleDayOfWeek(), "기준 요일", errors, false, 1, 31);
        ValidatorUtils.checkInteger(cpnIssuRuleUpdateDto.getRuleHour(), "기준 시", errors, false, 0, 23);
        ValidatorUtils.checkInteger(cpnIssuRuleUpdateDto.getRuleMinute(), "기준 분", errors, false, 0, 59);
        ValidatorUtils.checkInteger(cpnIssuRuleUpdateDto.getMsgDelayHr(), "메시지 지연 시간", errors, false, 0, null);

        if (cpnIssuRuleUpdateDto.getCpnIssuRuleType().equals(CpnIssuRuleType.STAMP)) {
            // 스탬프 쿠폰 발급 규칙은 시스템 내 한개만 존재해야 함.
            // 발급 시 스탬프가 차감되므로, 여러 개의 규칙이 있으면 처리 어려움
            if (cpnIssuRuleRepository.countByCpnIssuRuleTypeAndIdNot(CpnIssuRuleType.STAMP,
                cpnIssuRule.getId()) > 0) {
                errors.reject("규칙 중복 오류", "스탬프 규칙은 시스템 내 한개만 있어야 합니다");
            }
        }
        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }


}

