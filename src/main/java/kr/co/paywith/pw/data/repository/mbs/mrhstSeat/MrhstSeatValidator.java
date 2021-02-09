package kr.co.paywith.pw.data.repository.mbs.mrhstSeat;

import kr.co.paywith.pw.component.ValidatorUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class MrhstSeatValidator {


    public void validate(MrhstSeatDto mrhstSeatDto, Errors errors) {
      ValidatorUtils.checkInteger(mrhstSeatDto.getSeatCnt(), "시트 수", errors, true, 1, 99);
      ValidatorUtils.checkString(mrhstSeatDto.getNm(), "시트 명", errors, false, 1, 99);
      ValidatorUtils.checkObjectNull(mrhstSeatDto.getMrhstId(), "매장", errors);

        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

    public void validate(MrhstSeatUpdateDto mrhstSeatUpdateDto, Errors errors) {
      ValidatorUtils.checkInteger(mrhstSeatUpdateDto.getSeatCnt(), "시트 수", errors, true, 1, 99);
      ValidatorUtils.checkString(mrhstSeatUpdateDto.getNm(), "시트 명", errors, false, 1, 99);


        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

}
