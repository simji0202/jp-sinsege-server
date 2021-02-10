package kr.co.paywith.pw.data.repository.code.addrCode;


import kr.co.paywith.pw.component.ValidatorUtils;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.AdminRole;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class AddrCodeValidator {


  public void validate(Account currentUser, AddrCodeDto addrCodeDto, Errors errors) {
    ValidatorUtils.checkObjectNull(currentUser, "인증", errors);
    if (currentUser.getAdmin() != null && currentUser.getAdmin().getRoles()
        .contains(AdminRole.ADMIN_MASTER) // 전체 관리자
    ) {

    } else {
      // 적절한 권한이 없으면 오류
      errors.reject("권한 없음", "삭제 권한이 없습니다");
    }
    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

}
