package kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl;


import java.util.Optional;
import kr.co.paywith.pw.data.repository.admin.AdminRepository;
import kr.co.paywith.pw.data.repository.user.user.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class MrhstTrmnlValidator {


  @Autowired
  private UserInfoRepository userInfoRepository;

  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private MrhstTrmnlRepository mrhstTrmnlRepository;

  public void validate(MrhstTrmnlDto mrhstTrmnlDto, Errors errors) {

    // 아이디 중복 확인
    if (isIdDuplicated(mrhstTrmnlDto.getUserId(), null)) {
      errors.reject("아이디 중복", "중복되는 아이디가 있습니다");
    };

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  public void validate(MrhstTrmnlUpdateDto mrhstTrmnlUpdateDto, MrhstTrmnl mrhstTrmnl,  Errors errors) {

    // 아이디 중복 확인
    if (isIdDuplicated(mrhstTrmnlUpdateDto.getUserId(), mrhstTrmnl.getId())) {
      errors.reject("아이디 중복", "중복되는 아이디가 있습니다");
    };

    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  public void validate(MrhstTrmnlPwUpdateDto mrhstTrmnlPwUpdateDto, Errors errors) {


    // TODO BeginEventDateTime
    // TODO CloseEnrollmentDateTime
  }

  /**
   * 아이디 중복 검사
   *
   * @return 중복이 존재하면 true
   */
  private boolean isIdDuplicated(String userId, Integer id) {
    // Admin 중복 조회
    if (adminRepository.findByAdminId(userId).isPresent()) {
      return true;
    }

    // MrhstTrmnl 중복 조회
    Optional<MrhstTrmnl> mrhstTrmnlOptional = mrhstTrmnlRepository.findByUserId(userId);
    if (mrhstTrmnlOptional.isPresent()) { // 수정하는 경우가 있어 분기 처리
      if (id == null || !id.equals(mrhstTrmnlOptional.get().getId())) { // 본인이 아닌 경우
        return true;
      }
    }

    // UserInfo 중복 조회
    if (userInfoRepository.findByUserId(userId).isPresent()) {
      return true;
    }

    return false;
  }
}
