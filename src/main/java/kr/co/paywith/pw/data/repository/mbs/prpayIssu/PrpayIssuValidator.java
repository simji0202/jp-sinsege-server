package kr.co.paywith.pw.data.repository.mbs.prpayIssu;

import kr.co.paywith.pw.component.ValidatorUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PrpayIssuValidator {


    public void validate(PrpayIssuDto prpayIssuDto, Errors errors) {

        ValidatorUtils.checkString(prpayIssuDto.getPrpayIssuNm(), "선불카드 발급 명", errors, false, 1, 30);
        ValidatorUtils.checkInteger(prpayIssuDto.getCnt(), "선불카드 발급 매수", errors, true, 0, null);

        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

    public void validate(PrpayIssuUpdateDto prpayIssuUpdateDto, Errors errors) {

        ValidatorUtils.checkString(prpayIssuUpdateDto.getPrpayIssuNm(), "선불카드 발급 명", errors, false, 1, 30);

        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

}
