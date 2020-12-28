package kr.co.paywith.pw.data.repository.user.userApp;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UserAppValidator {


    public void validate(UserAppDto userAppDto, Errors errors) {

        // 키 null 확인(userInfo, trmnlId)
        if ( ( userAppDto != null && userAppDto.getUserInfo() != null  ) && userAppDto.getUserInfo().getId() == null || userAppDto.getTrmnlId() == null) {
            errors.reject("필수값 없음", "필수값이 없습니다");
        }

        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

    public void validate(UserAppUpdateDto userAppUpdateDto, Errors errors) {


        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }


}
