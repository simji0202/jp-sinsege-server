package kr.co.paywith.pw.data.repository.mbs.bbs;


import kr.co.paywith.pw.common.ValidatorUtils;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnDto;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnUpdateDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class BbsValidator {


    public void validate(BbsDto bbsDto, Errors errors) {

        ValidatorUtils.checkString(bbsDto.getBbsSj(), "제목", errors, false, 0, 200);

        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

    public void validate(BbsUpdateDto bbsUpdateDto, Errors errors) {

        ValidatorUtils.checkString(bbsUpdateDto.getBbsSj(), "제목", errors, false, 0, 200);

        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

}
