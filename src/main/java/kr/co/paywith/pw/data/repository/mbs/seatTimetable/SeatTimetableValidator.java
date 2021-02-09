package kr.co.paywith.pw.data.repository.mbs.seatTimetable;

import kr.co.paywith.pw.data.repository.enumeration.SeatSttsType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class SeatTimetableValidator {


//    public void validate(SeatTimetableDto seatTimetableDto, Errors errors) {
//
//
//        // TODO BeginEventDateTime
//        // TODO CloseEnrollmentDateTime
//    }

    public void validate(SeatTimetableUpdateDto seatTimetableUpdateDto, SeatTimetable seatTimetable, Errors errors) {
        if (seatTimetableUpdateDto.getSeatSttsType().equals(SeatSttsType.RSRV)) {
            errors.reject("수정 불가", "Delng 등록으로 바꿔야 합니다");
        }

        if (seatTimetable.getSeatSttsType().equals(SeatSttsType.RSRV)) {
            errors.reject("수정 불가", "이미 예약된 좌석은 상태를 바꿀 수 없습니다");
        }

        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

}
