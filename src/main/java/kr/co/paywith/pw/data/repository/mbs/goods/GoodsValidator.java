package kr.co.paywith.pw.data.repository.mbs.goods;


import kr.co.paywith.pw.component.ValidatorUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class GoodsValidator {


  public void validate(GoodsDto goodsDto, Errors errors) {

    ValidatorUtils.checkInteger(goodsDto.getScorePlusCnt(), "상품 점수", errors, false, 0, null);

    ValidatorUtils.checkInteger(goodsDto.getScorePlusCnt(), "스탬프 개수", errors, false, 0, null);

    ValidatorUtils.checkObjectNull(goodsDto.getGoodsGrp(), "상품 그룹", errors);

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  public void validate(GoodsUpdateDto goodsUpdateDto, Errors errors) {

    ValidatorUtils.checkInteger(goodsUpdateDto.getScorePlusCnt(), "상품 점수", errors, false, 0, null);

    ValidatorUtils.checkInteger(goodsUpdateDto.getScorePlusCnt(), "스탬프 개수", errors, false, 0, null);

    ValidatorUtils.checkObjectNull(goodsUpdateDto.getGoodsGrp(), "상품 그룹", errors);

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

}
