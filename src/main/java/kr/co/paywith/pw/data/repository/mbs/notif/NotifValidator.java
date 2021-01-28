package kr.co.paywith.pw.data.repository.mbs.notif;

import java.util.stream.Collectors;
import kr.co.paywith.pw.component.ValidatorUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class NotifValidator {


    public void validate(NotifDto notifDto, Errors errors) {

      ValidatorUtils.checkString(notifDto.getNotifSj(), "제목", errors, false,1, 60);
      ValidatorUtils.checkString(notifDto.getNotifCn(), "본문", errors, true,1, 4000);


      // 푸시 보낼 대상이 있어야 함
      switch (notifDto.getNotifType()) {
        case USER:
          ValidatorUtils.checkObjectNull(notifDto.getNotifUserList(), "대상 회원 없음", errors);
          // mrhstId 없으면 제외
          notifDto.setNotifUserList(
              notifDto.getNotifUserList().stream().filter(notifUser -> notifUser.getUserId() != null)
                  .collect(Collectors.toList()));
          if (notifDto.getNotifUserList().size() == 0) {
            errors.reject("대상 회원 없음", "목록에 대상 회원을 넣어야 합니다");
          }
          break;
        case MRHST_TRMNL:
          ValidatorUtils.checkObjectNull(notifDto.getNotifMrhstList(), "대상 매장 없음", errors);
          // mrhstId 없으면 제외
          notifDto.setNotifMrhstList(
              notifDto.getNotifMrhstList().stream().filter(notifMrhst -> notifMrhst.getMrhstId() != null)
                  .collect(Collectors.toList()));
          if (notifDto.getNotifMrhstList().size() == 0) {
            errors.reject("대상 매장 없음", "목록에 대상 매장을 넣어야 합니다");
          }
          break;
        default:
          errors.reject("전송 대상", "전송 대상(푸시 종류)을 지정해야 합니다");
          break;
      }

        // TODO BeginEventDateTime
        // TODO CloseEnrollmentDateTime
    }

//    public void validate(NotifUpdateDto notifUpdateDto, Errors errors) {
//
//
//        // TODO BeginEventDateTime
//        // TODO CloseEnrollmentDateTime
//    }

}
