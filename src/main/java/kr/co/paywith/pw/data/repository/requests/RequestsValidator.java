package kr.co.paywith.pw.data.repository.requests;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class RequestsValidator {

  public void validate(RequestsDto requestsDto, Errors errors) {
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


  public void validate(RequestsUpdateDto requestsUpdateDto, Errors errors) {
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
