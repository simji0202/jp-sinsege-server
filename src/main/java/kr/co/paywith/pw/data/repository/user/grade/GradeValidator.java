package kr.co.paywith.pw.data.repository.user.grade;


import kr.co.paywith.pw.component.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class GradeValidator {

  @Autowired
  private GradeRepository gradeRepository;


  public void validate(GradeDto gradeDto, Errors errors) {

    // 등급 순서는 값이 없거나 0 이상이어야 함
    ValidatorUtils.checkInteger(gradeDto.getSort(), "순서", errors, false, 0, null);

    if (gradeDto.getSort() != null && gradeDto.getSort() > 0) {
      // 기존 sort값 바로 다음이어야 함
      if (gradeRepository.findBySort(gradeDto.getSort() - 1).isEmpty()) {
        errors.reject("등급 오류", "등급 순서에 오류가 있습니다");
      }
    }


    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  public void validate(GradeUpdateDto gradeUpdateDto, Errors errors) {

    // 등급 순서는 값이 없거나 0 이상이어야 함
    ValidatorUtils.checkInteger(gradeUpdateDto.getSort(), "순서", errors, false, 0, null);

    if (gradeUpdateDto.getSort() != null && gradeUpdateDto.getSort() > 0) {
      // 기존 sort값 바로 다음이어야 함
      if (gradeRepository.findBySort(gradeUpdateDto.getSort() - 1).isEmpty()) {
        errors.reject("등급 오류", "등급 순서에 오류가 있습니다");
      }
    }

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }


}
