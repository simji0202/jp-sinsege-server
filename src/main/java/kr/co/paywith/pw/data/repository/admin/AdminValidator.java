package kr.co.paywith.pw.data.repository.admin;

import java.util.Optional;
import kr.co.paywith.pw.common.ValidatorUtils;
import kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl.MrhstTrmnl;
import kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl.MrhstTrmnlRepository;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class AdminValidator {

  @Autowired
  private UserInfoRepository userInfoRepository;

  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private MrhstTrmnlRepository mrhstTrmnlRepository;

  public void validate(AdminDto adminDto, Errors errors) {

    // 아이디 중복 확인
    if (isIdDuplicated(adminDto.getAdminId(), adminDto.getId())) {
      errors.reject("아이디 중복", "중복되는 아이디가 있습니다");
    };

    ValidatorUtils.checkString(adminDto.getEmailAddr(), "이메일", errors, false, 3, 50);

    ValidatorUtils.checkString(adminDto.getMobileNum(), "휴대폰번호", errors, false, 3, 30);

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }


  public void validate(AdminUpdateDto adminDto, Errors errors) {

    // 아이디 중복 확인
    if (isIdDuplicated(adminDto.getAdminId(), adminDto.getId())) {
      errors.reject("아이디 중복", "중복되는 아이디가 있습니다");
    };

    ValidatorUtils.checkString(adminDto.getEmailAddr(), "이메일", errors, false, 3, 50);

    ValidatorUtils.checkString(adminDto.getMobileNum(), "휴대폰번호", errors, false, 3, 30);

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }



  public void validate(AdminPwUpdateDto adminPwUpdateDto, Errors errors) {
    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }


  /**
   * 아이디 중복 검사
   *
   * @return 중복이 존재하면 true
   */
  private boolean isIdDuplicated(String adminId, Integer id) {
    // Admin 중복 조회
    Optional<Admin> adminOptional = adminRepository.findByAdminId(adminId);
    if (adminOptional.isPresent()) { // 수정하는 경우가 있어 분기 처리
      if (id == null || !id.equals(adminOptional.get().getAdminId())) { // 본인이 아닌 경우
        return true;
      }
    }

    // MrhstTrmnl 중복 조회
    // MrhstTrmnl 중복 조회
    if (mrhstTrmnlRepository.findByUserId(adminId).isPresent()) {
      return true;
    }

    // UserInfo 중복 조회
    if (userInfoRepository.findByUserId(adminId).isPresent()) {
      return true;
    }

    return false;
  }

}
