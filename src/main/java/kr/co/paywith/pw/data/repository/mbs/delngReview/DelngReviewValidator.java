package kr.co.paywith.pw.data.repository.mbs.delngReview;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class DelngReviewValidator {
  
  @Autowired
  private DelngReviewRepository delngReviewRepository;


    public void validate(DelngReviewDto delngReviewDto, Errors errors) {
        Optional<Delng> delngOptional = delngReviewRepository.findByDelngId(delngReviewDto.getDelng().getId());
        if (delngOptional.isPresent()) {
          errors.reject("중복", "주문 당 리뷰는 한 건만 작성 가능합니다");
        }

        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

    public void validate(DelngReviewUpdateDto delngReviewUpdateDto, Errors errors) {


        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

}
