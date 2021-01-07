package kr.co.paywith.pw.data.repository.mbs.cpn;


import kr.co.paywith.pw.data.repository.account.Account;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CpnValidator {


    public void validate(CpnDto cpnDto, Errors errors) {


        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

    public void validate(CpnUpdateDto cpnUpdateDto, Errors errors) {


        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

    public void validate(Account currentUser, Cpn cpn, Errors errors) {

        if (currentUser.getUserInfo() != null &&
                !currentUser.getAccountId().equalsIgnoreCase(cpn.getUserInfo().getUserId())) {
            errors.reject("쿠폰 권한", "본인에게 발급된 쿠폰이 아닙니다  ");
        }

        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }
}
