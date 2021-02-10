package kr.co.paywith.pw.data.repository.mbs.goodsStockHist;


import javax.validation.Valid;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
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


  public void validate(@Valid GoodsStockHistDto goodsStockHistDto, Account currentUser, Errors errors) {
    if (currentUser.getMrhstTrmnl() != null && currentUser.getAdmin() != null) {
      errors.reject("권한 없음", "매장과 관리자만 재고관리가 가능합니다");
    }

    switch (goodsStockHistDto.getGoodsStockHistType()) {
      case IN:
        if (goodsStockHistDto.getCnt() <= 0) {
          errors.reject("변수 오류", "재고수는 1 이상이어야 합니다");
        }
        break;
      case OUT:
        if (goodsStockHistDto.getCnt() >= 0) {
          errors.reject("변수 오류", "재고수는 -1 이하여야 합니다");
        }
        break;
    }

    if (goodsStockHistDto.getGoodsStock().getId() == null &&
      (goodsStockHistDto.getGoodsStock().getGoodsId() == null ||
          goodsStockHistDto.getGoodsStock().getMrhstId() == null)
    ) {
      errors.reject("재고 정보 오류", "재고상태를 식별할 수 있는 값이 없습니다");

    } else {
      // 재고 정보 입력했을 때 추가 검증
      GoodsStock goodsStock = null;
      if (goodsStockHistDto.getGoodsStock().getId() != null) {
        goodsStock = goodsStockRepository.findById(goodsStockHistDto.getGoodsStock().getId()).orElse(null);
        if (goodsStock == null) {
          errors.reject("재고 정보 오류", "재고 조회 식별값 오류");
        }
      } else {
        goodsStock = goodsStockRepository.findByGoods_IdAndMrhst_Id(
            goodsStockHistDto.getGoodsStock().getGoodsId(),
            goodsStockHistDto.getGoodsStock().getMrhstId()).orElse(null);

        // TODO goodsId, mrhstId 유효한지 확인??
      }

      if (goodsStock != null) {
        if (goodsStock.getId() == null) {
          // 재고 신규 등록
          if (goodsStockHistDto.getGoodsStockHistType().equals(GoodsStockHistType.OUT)) {
            errors.reject("변수 오류", "최초 재고 이력 등록은 반입이어야 합니다");
          }
        } else {
          // 기존 재고 확인해서 재고 -로 저장하는 것 방지
          if (goodsStock.getCnt() + goodsStockHistDto.getCnt() < 0) {
            errors.reject("재고 오류", "재고는 -가 될 수 없습니다");
          }
        }
      }
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
