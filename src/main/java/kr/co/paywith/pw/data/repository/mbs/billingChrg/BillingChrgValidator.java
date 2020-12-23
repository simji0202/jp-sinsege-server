package kr.co.paywith.pw.data.repository.mbs.billingChrg;


import kr.co.paywith.pw.data.repository.mbs.cpn.CpnDto;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnUpdateDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class BillingChrgValidator {


    public void validate(BillingChrgDto billingChrgDto, Errors errors) {


        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

    public void validate(BillingChrgUpdateDto billingChrgUpdateDto, Errors errors) {


        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

}
