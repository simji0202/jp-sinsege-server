package kr.co.paywith.pw.data.repository.mbs.useReq;


import kr.co.paywith.pw.data.repository.user.userApp.UserAppDto;
import kr.co.paywith.pw.data.repository.user.userApp.UserAppUpdateDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UseReqValidator {


    public void validate(UseReqDto useReqDto, Errors errors) {


        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

    public void validate(UseReqUpdateDto useReqUpdateDto, Errors errors) {


        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }


}
