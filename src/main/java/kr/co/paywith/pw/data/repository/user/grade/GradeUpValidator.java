package kr.co.paywith.pw.data.repository.user.grade;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class GradeUpValidator {


    public void validate(GradeUpDto gradeUpDto, Errors errors) {

        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

    public void validate(GradeUpUpdateDto gradeUpdateDto, Errors errors) {

    }
}
