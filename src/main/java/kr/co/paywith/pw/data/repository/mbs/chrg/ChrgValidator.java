package kr.co.paywith.pw.data.repository.mbs.chrg;

import kr.co.paywith.pw.component.ValidatorUtils;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import kr.co.paywith.pw.data.repository.user.user.UserInfoRepository;
import kr.co.paywith.pw.data.repository.user.userCard.UserCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ChrgValidator {

    @Autowired
    private UserInfoRepository userInfoRepository;

    public void validate(ChrgDto chrgDto, Errors errors) {

        // 데이터 형식 검증
        ValidatorUtils.checkInt(chrgDto.getChrgAmt(), "충전 금액", errors, 1, 99999999);
        ValidatorUtils.checkObjectNull(chrgDto.getChrgTypeCd(), "충전 종류", errors);

        ValidatorUtils.checkObjectNull(chrgDto.getUserInfo(), "회원 정보", errors);

        UserInfo userInfo = userInfoRepository.findById(chrgDto.getUserInfo().getId()).orElse(null);
        if (userInfo == null) {
            errors.reject("회원정보", "유효한 회원정보가 없습니다");
        } if (!userInfo.getUserCard().getPrpayActiveFl()) {
            errors.reject("회원정보", "선불기능이 정지되었습니다");
        }

        // 로직 검증
        switch (chrgDto.getChrgTypeCd()) {
            case PW:
            case POS:
                // 단말기에서 온 요청이면 매장 정보가 있어야 한다
                ValidatorUtils.checkObjectNull(chrgDto.getMrhstId(), "매장ID", errors);
                ValidatorUtils.checkObjectNull(chrgDto.getMrhstNm(), "매장명", errors);
                ValidatorUtils.checkObjectNull(chrgDto.getMrhstTrmnlId(), "단말기", errors);
                ValidatorUtils.checkObjectNull(chrgDto.getSetleConfmNo(), "결제 승인 번호", errors);
                break;
            case SYS:
                // 시스템에 직접 충전한다면 권한이 있어야 한다
                // kms: TODO 이 메소드 파라미터로 currentUser 받아야 한다
                break;
            case APP:
                // 앱에서는 PG 충전밖에 없으므로, 유효한 PG 결제를 했어야 한다
//                ValidatorUtils.checkObjectNull(chrgDto.getPgPay(), "결제", errors);
                break;
            default:
                break;
        }

        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

    public void validate(ChrgUpdateDto chrgUpdateDto, Errors errors) {


        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

    public void validate(Account currentUser, ChrgDeleteDto chrgDeleteDto, Chrg chrg, Errors errors) {
        if (chrg.getCancelRegDttm() != null) {
            errors.reject("기취소 오류", "이미 취소된 충전입니다");
        }

        // TODO 회원이지만 본인이 아닐경우 오류

        // 잔액이 취소하기 충분한지 확인
        UserCard userCard = chrg.getUserInfo().getUserCard();
        if (userCard.getPrpayAmt() < chrg.getChrgAmt()) {
            errors.reject("선불 잔액 오류", "취소할 잔액이 부족합니다");
        }
    }
}
