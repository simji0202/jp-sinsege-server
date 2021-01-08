package kr.co.paywith.pw.data.repository.mbs.cpnIssuRule;


import kr.co.paywith.pw.component.ValidatorUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CpnIssuRuleValidator {


    public void validate(CpnIssuRuleDto cpnIssuRuleDto, Errors errors) {

        ValidatorUtils.checkString(cpnIssuRuleDto.getMsgSj(), "메시지 제목", errors, false, 1, 100);
        ValidatorUtils.checkString(cpnIssuRuleDto.getMsgCn(), "메시지 본문", errors, false, 1, 4000);

        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

    public void validate(CpnIssuRuleUpdateDto cpnIssuRuleUpdateDto, Errors errors) {

        ValidatorUtils.checkString(cpnIssuRuleUpdateDto.getMsgSj(), "메시지 제목", errors, false, 1, 100);
        ValidatorUtils.checkString(cpnIssuRuleUpdateDto.getMsgCn(), "메시지 본문", errors, false, 1, 4000);

        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }


}

