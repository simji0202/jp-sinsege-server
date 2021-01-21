package kr.co.paywith.pw.data.repository.mbs.scoreHist;

import kr.co.paywith.pw.component.ValidatorUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ScoreHistValidator {


    public void validate(ScoreHistDto scoreHistDto, Errors errors) {

        ValidatorUtils.checkObjectNull(scoreHistDto.getUserInfo(), "회원정보", errors);
        ValidatorUtils.checkObjectNull(scoreHistDto.getUserInfo().getId(), "회원정보", errors);

        ValidatorUtils.checkInteger(scoreHistDto.getScoreAmt(), "점수", errors, true, -9999999, 999999);

        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

}
