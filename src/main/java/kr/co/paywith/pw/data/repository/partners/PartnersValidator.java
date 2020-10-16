package kr.co.paywith.pw.data.repository.partners;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PartnersValidator {


  public void validate(Partners partners, Errors errors) {
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



  public void validate(PartnersDto partnersDto, Errors errors) {
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


  public void validate(PartnersChargeDto partnersChargeDto, Errors errors) {
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
