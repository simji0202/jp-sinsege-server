package kr.co.paywith.pw.data.repository.mbs.goodsgrp;


import kr.co.paywith.pw.component.ValidatorUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class GoodsGrpValidator {


  public void validate(GoodsGrpDto goodsGrpDto, Errors errors) {

    ValidatorUtils.checkObjectNull(goodsGrpDto.getBrand(), "브랜드", errors);
    ValidatorUtils.checkObjectNull(goodsGrpDto.getBrand().getId(), "브랜드", errors);
    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  public void validate(GoodsGrpUpdateDto goodsGrpUpdateDto, Errors errors) {


    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

}
