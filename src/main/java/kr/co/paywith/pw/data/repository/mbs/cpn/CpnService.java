package kr.co.paywith.pw.data.repository.mbs.cpn;


import kr.co.paywith.pw.data.repository.enumeration.CpnIssuRuleCd;
import kr.co.paywith.pw.data.repository.enumeration.CpnSttsCd;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CpnService {

  @Autowired
  private CpnRepository cpnRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  private UserInfoRepository userInfoRepository;

  /**
   * 정보 등록
   */
  @Transactional
  public Cpn create(Cpn cpn) {

    // 데이터베이스 값 갱신
    Cpn newCpn = this.cpnRepository.save(cpn);

    return newCpn;
  }


  /**
   * 정보 갱신
   */
  @Transactional
  public Cpn update(CpnUpdateDto cpnUpdateDto, Cpn existCpn) {

    // 입력값 대입
    this.modelMapper.map(cpnUpdateDto, existCpn);

    // 데이터베이스 값 갱신
    this.cpnRepository.save(existCpn);

    return existCpn;
  }


  /**
   * 쿠폰 취소. 스탬프 쿠폰이면 스탬프 복원
   *
   * @param cpn 취소할 쿠폰
   */
  @Transactional
  public void delete(Cpn cpn) {
    cpn.setCpnSttsCd(CpnSttsCd.INVALID);

    if (cpn.getCpnIssu().getCpnRule() != null &&
        cpn.getCpnIssu().getCpnRule().getCpnIssuRuleCd().equals(CpnIssuRuleCd.S) ||
        cpn.getCpnIssu().getCpnRule().getCpnIssuRuleCd().equals(CpnIssuRuleCd.SI) // 스탬프 쿠폰이면
    ) {
      // 회원의 스탬프 개수 복원
      // kms TODO 쿠폰 마스터와 동일한 회원의 브랜드 정보에서 스탬프 차감 필요
//      cpn.getUserInfo().getUserStamp().
      // userInfo 저장
      // kms: 이 save는 repository? userInfoService? 어디서 해야 하는지
//      userInfoRepository.
    }
    cpnRepository.save(cpn);
  }
}
