package kr.co.paywith.pw.data.repository.mbs.cpnIssu;


import java.time.ZonedDateTime;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CpnIssuValidator {

  public void validate(CpnIssuDto cpnIssuDto, Errors errors) {

    if (cpnIssuDto.getValidEndDttm() != null &&
        cpnIssuDto.getValidEndDttm().isBefore(ZonedDateTime.now())) {
      // 새로 발급하는 쿠폰은 현재 시간보다 이후여야 함
      errors.reject("쿠폰 유효 일시 오류", "유효일시는 현재시간 이후여야 합니다");
    }

    if (cpnIssuDto.getCpnList() == null || cpnIssuDto.getCpnList().size() == 0) {
      errors.reject("쿠폰 발급 회원 정보 누락", "발급할 회원정보가 필요합니다");
    } else {
      for (CpnDto cpn : cpnIssuDto.getCpnList()) {
        if (cpn == null ||
            cpn.getUserInfo() == null ||
            cpn.getUserInfo().getId() == null) {
          errors.reject("쿠폰 발급 회원 정보 누락", "발급할 회원정보가 필요합니다");
          break;
        }
      }
    }

    if (cpnIssuDto.getCpnMaster() == null || cpnIssuDto.getCpnMaster().getId() == null) {
        errors.reject("쿠폰 정보 없음", "발급할 쿠폰 종류가 없습니다");
    }

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  public void validate(CpnIssuUpdateDto cpnIssuUpdateDto, Errors errors) {

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

}
