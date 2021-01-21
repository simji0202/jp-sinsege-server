package kr.co.paywith.pw.data.repository.mbs.cm;


import kr.co.paywith.pw.component.ValidatorUtils;
import kr.co.paywith.pw.data.repository.enumeration.CpnMasterType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CpnMasterValidator {


  public void validate(CpnMasterDto cpnMasterDto, Errors errors) {

    // 쿠폰 마스터 종류 (필수 체크)
    ValidatorUtils.checkObjectNull(cpnMasterDto.getCpnMasterType(), "쿠폰 마스터 종류", errors);

    ValidatorUtils.checkString(cpnMasterDto.getCpnNm(), "쿠폰 명", errors, true, 1, 100);

    ValidatorUtils.checkInteger(cpnMasterDto.getValidDay(), "유효기간", errors, true, 1, null);
//

    // 1+1 쿠폰일 경우 쿠폰 적용 상픔 확인
    if (CpnMasterType.GOODS.equals(cpnMasterDto.getCpnMasterType())) {
      ValidatorUtils.checkObjectNull(cpnMasterDto.getGoodsId(), "쿠폰 적용 상품", errors);
    }

    // 금액 쿠폰 경우  할인금액 확인
    if (CpnMasterType.AMT.equals(cpnMasterDto.getCpnMasterType())) {
      ValidatorUtils.checkInteger(cpnMasterDto.getCpnAmt(), "할인금액", errors, false, 1, 99999999);
    }

    // 할인 쿠폰일 경우 할인율 확인
    if (CpnMasterType.RATIO.equals(cpnMasterDto.getCpnMasterType())) {
      ValidatorUtils.checkInteger(cpnMasterDto.getCpnRatio(), "할인율", errors, false, 1, 100);
    }

    ValidatorUtils.checkString(cpnMasterDto.getCpnCd(), "쿠폰 코드", errors, false, 1, 20);
    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  public void validate(CpnMasterUpdateDto cpnMasterUpdateDto, Errors errors) {

    ValidatorUtils.checkString(cpnMasterUpdateDto.getCpnNm(), "쿠폰 명", errors, true, 1, 100);

    ValidatorUtils.checkInteger(cpnMasterUpdateDto.getValidDay(), "유효기간", errors, true, 1, null);

    ValidatorUtils.checkString(cpnMasterUpdateDto.getCpnCd(), "쿠폰 코드", errors, false, 1, 20);

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }


  public void validate(CpnMasterUpdateDto cpnMasterUpdateDto, CpnMaster cpnMaster,  Errors errors) {

    ValidatorUtils.checkString(cpnMasterUpdateDto.getCpnNm(), "쿠폰 명", errors, true, 1, 100);

    ValidatorUtils.checkInteger(cpnMasterUpdateDto.getValidDay(), "유효기간", errors, true, 1, null);

    ValidatorUtils.checkString(cpnMasterUpdateDto.getCpnCd(), "쿠폰 코드", errors, false, 1, 20);

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }


}
