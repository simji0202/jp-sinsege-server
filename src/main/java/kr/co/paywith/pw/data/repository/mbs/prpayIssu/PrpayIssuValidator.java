package kr.co.paywith.pw.data.repository.mbs.prpayIssu;

import kr.co.paywith.pw.component.ValidatorUtils;
import kr.co.paywith.pw.data.repository.mbs.prpay.Prpay;
import kr.co.paywith.pw.data.repository.mbs.prpay.PrpayRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PrpayIssuValidator {

    @Autowired
    private PrpayRepository prpayRepository;

    public void validate(PrpayIssuDto prpayIssuDto, Errors errors) {

        ValidatorUtils.checkString(prpayIssuDto.getPrpayIssuNm(), "선불카드 발급 명", errors, false, 1, 30);
        ValidatorUtils.checkInteger(prpayIssuDto.getCnt(), "선불카드 발급 매수", errors, true, 0, null);

        if (prpayIssuDto.getPrpayList() != null && prpayIssuDto.getPrpayList().size() > 0) {
            // 발급 요청시 기입력한 선불카드 번호 오류가 있는지 확인
            for (Prpay prpay : prpayIssuDto.getPrpayList()) {
                // 번호 형식은 최대한 자율로 두되 중복은 없도록
                if (prpayRepository.findByPrpayNo(prpay.getPrpayNo()).isPresent()) {
                    errors.reject("선불카드 번호", "중복된 번호가 있습니다");
                    break;
                }
            }
        }

        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

    public void validate(PrpayIssuUpdateDto prpayIssuUpdateDto, Errors errors) {

        ValidatorUtils.checkString(prpayIssuUpdateDto.getPrpayIssuNm(), "선불카드 발급 명", errors, false, 1, 30);

        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

}
