package kr.co.paywith.pw.data.repository.mbs.cpnRule;


import kr.co.paywith.pw.component.ValidatorUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CpnRuleValidator {


    public void validate(CpnRuleDto cpnRuleDto, Errors errors) {

        ValidatorUtils.checkString(cpnRuleDto.getMsgSj(), "메시지 제목", errors, false, 1, 100);
        ValidatorUtils.checkString(cpnRuleDto.getMsgCn(), "메시지 본문", errors, false, 1, 4000);

        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

    public void validate(CpnRuleUpdateDto cpnRuleUpdateDto, Errors errors) {

        ValidatorUtils.checkString(cpnRuleUpdateDto.getMsgSj(), "메시지 제목", errors, false, 1, 100);
        ValidatorUtils.checkString(cpnRuleUpdateDto.getMsgCn(), "메시지 본문", errors, false, 1, 4000);

        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }


}

