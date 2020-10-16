package kr.co.paywith.pw.data.repository.staistics;

import kr.co.paywith.pw.data.repository.SearchForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class StatisticsValidator {



  public void validate(SearchForm searchForm, Errors errors) {
    //if (customerDto.getBasePrice() > customerDto.getMaxPrice() && customerDto.getMaxPrice() > 0) {
    //errors.reject("wrongPrices", "Values fo prices are wrong");
    //}

    //LocalDateTime endEventDateTime = customerDto.getEndEventDateTime();
    //if (endEventDateTime.isBefore(customerDto.getBeginEventDateTime()) ||
    //endEventDateTime.isBefore(customerDto.getCloseEnrollmentDateTime()) ||
    //endEventDateTime.isBefore(customerDto.getBeginEnrollmentDateTime())) {
    //errors.rejectValue("endEventDateTime", "wrongValue", "endEventDateTime is wrong");
    //}

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }


}
