package kr.co.paywith.pw.data.repository.user.userInfo;


import java.util.Optional;
import kr.co.paywith.pw.data.repository.admin.AdminRepository;
import kr.co.paywith.pw.data.repository.enumeration.CertTypeCd;
import kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl.MrhstTrmnlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UserInfoValidator {


  @Autowired
  private UserInfoRepository userInfoRepository;

  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private MrhstTrmnlRepository mrhstTrmnlRepository;

  public void validate(UserInfoDto userInfoDto, Errors errors) {

    // 아이디 중복 확인
    if (isIdDuplicated(userInfoDto.getUserId(), userInfoDto.getCertTypeCd(), userInfoDto.getId())) {
      errors.reject("아이디 중복", "중복되는 아이디가 있습니다");
    }

    // TODO 기존 가입 확인. certTypeCd에 따라 중복 확인(정책 검토 필요)

    // 전화번호 오류 검증
    if (isMobileNumInvalid(userInfoDto.getMobileNum())) {
      errors.reject("전화번호 오류", "전화번호 형식이 맞지 않습니다");
    }

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  public void validate(UserInfoUpdateDto userInfoUpdateDto, Errors errors) {
    // 아이디 중복 확인
    if (isIdDuplicated(userInfoUpdateDto.getUserId(), userInfoUpdateDto.getCertTypeCd(),
        userInfoUpdateDto.getId())) {
      errors.reject("아이디 중복", "중복되는 아이디가 있습니다");
    }

    // 전화번호 오류 검증
    if (isMobileNumInvalid(userInfoUpdateDto.getMobileNum())) {
      errors.reject("전화번호 오류", "전화번호 형식이 맞지 않습니다");
    }

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  public void validate(UserInfoPwUpdateDto userInfoPwUpdateDto, Errors errors) {


    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  /**
   * 아이디 중복 검사
   *
   * @return 중복이 존재하면 true
   */
  private boolean isIdDuplicated(String userId, CertTypeCd certTypeCd, Integer id) {
    if (CertTypeCd.GUEST.equals(certTypeCd)) { // GUEST는 서버에서 아이디를 만들어 주므로 중복확인 통과 (GUEST#id 형식)
      return false;
    }
    // TODO 카카오 등 외부서비스도 필요시 통과

    // Admin 중복 조회
    if (adminRepository.findByAdminId(userId).isPresent()) {
      return true;
    }

    // MrhstTrmnl 중복 조회
    if (mrhstTrmnlRepository.findByUserId(userId).isPresent()) {
      return true;
    }

    // UserInfo 중복 조회
    Optional<UserInfo> userInfo = userInfoRepository.findByUserId(userId);
    if (userInfo.isPresent()) { // 수정하는 경우가 있어 분기 처리
      if (id == null || !id.equals(userInfo.get().getUserId())) { // 본인이 아닌 경우
        return true;
      }
    }
    return false;
  }

  /**
   * 휴대폰 번호 오류 확인
   *
   * @return 오류가 존재하면 true
   */
  private boolean isMobileNumInvalid(String mobileNum) {
    // masking 된 번호를 그대로 보내는 경우 오류
    if (mobileNum != null && mobileNum.indexOf("*") >= 0) {
      return true;
    }
    return false;
  }
}
