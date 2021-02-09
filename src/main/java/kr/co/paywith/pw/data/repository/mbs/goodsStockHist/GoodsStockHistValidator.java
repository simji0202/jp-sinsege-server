package kr.co.paywith.pw.data.repository.mbs.goodsStockHist;


import javax.validation.Valid;
import kr.co.paywith.pw.data.repository.enumeration.GoodsStockHistType;
import kr.co.paywith.pw.data.repository.mbs.goodsStock.GoodsStock;
import kr.co.paywith.pw.data.repository.mbs.goodsStock.GoodsStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class GoodsStockHistValidator {

  @Autowired
  private GoodsStockRepository goodsStockRepository;


  public void validate(@Valid GoodsStockHistDto goodsStockHistDto, Errors errors) {

    if (goodsStockHistDto.getGoodsStock().getId() != null) {
      GoodsStock goodsStock = goodsStockRepository.findById(goodsStockHistDto.getGoodsStock().getId()).get();
      if (goodsStock.getCnt() + goodsStockHistDto.getCnt() < 0) {
        errors.reject("재고 수 부족", "설정된 재고 수가 부족합니다");
      }

    } else {
      // 재고 신규 등록
      if (goodsStockHistDto.getGoodsStock().getGoodsId() == null ||
          goodsStockHistDto.getGoodsStock().getMrhstId() == null) {
        errors.reject("재고 정보 오류", "재고상태를 식별할 수 있는 값이 없습니다");
      }
      if (goodsStockHistDto.getGoodsStockHistType().equals(GoodsStockHistType.OUT)) {
        errors.reject("변수 오류", "최초 재고 이력 등록은 반입이어야 합니다");
      }

      // TODO goodsId, mrhstId 유효한지 확인??
    }

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

//  public void validate(GoodsUpdateDto goodsUpdateDto, Errors errors) {
//
//    ValidatorUtils.checkInt(goodsUpdateDto.getPlusScore(), "상품 점수", errors, 0, null);
//
//    ValidatorUtils.checkInt(goodsUpdateDto.getPlusStampCnt(), "스탬프 개수", errors, 0, null);
//
//    ValidatorUtils.checkObjectNull(goodsUpdateDto.getGoodsGrp(), "상품 그룹", errors);
//
//    // TODO BeginEventDateTime
//    // TODO CloseEnrollmentDateTime
//  }

}
