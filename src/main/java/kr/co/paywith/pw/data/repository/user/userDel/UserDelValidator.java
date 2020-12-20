package kr.co.paywith.pw.data.repository.user.userDel;


import kr.co.paywith.pw.data.repository.user.userApp.UserAppDto;
import kr.co.paywith.pw.data.repository.user.userApp.UserAppUpdateDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UserDelValidator {


    public void validate(UserDelDto userDelDto, Errors errors) {


        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

    public void validate(UserDelUpdateDto userDelUpdateDto, Errors errors) {


        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }


}
