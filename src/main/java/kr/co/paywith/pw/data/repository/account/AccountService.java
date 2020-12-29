package kr.co.paywith.pw.data.repository.account;

import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.admin.AdminRepository;
import kr.co.paywith.pw.data.repository.admin.AdminRole;
import kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl.MrhstTrmnl;
import kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl.MrhstTrmnlRepository;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService implements  UserDetailsService {

  @Autowired
  AdminRepository adminRepository;

  @Autowired
  UserInfoRepository userInfoRepository;

  @Autowired
  MrhstTrmnlRepository mrhstTrmnlRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  /**
   * @return
   * @throws UsernameNotFoundException
   */
  @Override
  public UserDetails loadUserByUsername(String logingId) throws UsernameNotFoundException {

    // Admin 테이블에서 로그인 정보 유무 확인
    Admin admin = adminRepository.findByAdminId(logingId).orElse(null);

    if ( admin != null ) {
      // 로그인 유저 정보 취득 후 CurrentUser에  정보 설정
      return new AccountAdapter(getAccountFromAdmin(admin));
    }

    // User 테이블에서 로그인 정보 유무 확인
    UserInfo userInfo = userInfoRepository.findByUserId(logingId).orElse(null);

    if ( userInfo != null ) {
      // 로그인 유저 정보 취득 후 CurrentUser에  정보 설정
      return new AccountAdapter(getAccountFromUserinfo(userInfo));
    }

    MrhstTrmnl mrhstTrmnl = mrhstTrmnlRepository.findByUserId(logingId).orElseThrow(() -> new UsernameNotFoundException(logingId));

    if ( mrhstTrmnl != null ) {
      // 로그인 유저 정보 취득 후 CurrentUser에  정보 설정
      return new AccountAdapter(getAccountFromMrhst(mrhstTrmnl));
    }

    //
    return new AccountAdapter(new Account());
  }


  private Collection<? extends GrantedAuthority> authorities(Set<AdminRole> roles) {
    return roles.stream()
        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
        .collect(Collectors.toSet());
  }


  /**
   * 관리자 유저 정보를 로그인 객체에 설정
   * @param admin
   * @return
   */
  private  Account getAccountFromAdmin(Admin admin) {

    Account account = new Account();
    account.setAccountId(admin.getAdminId());
    account.setAccountPw(admin.getAdminPw());
    account.setAccountNm(admin.getAdminNm());
    account.setAuthCd(admin.getAuthCd());
    account.setRoles(admin.getRoles());
    account.setAdmin(admin);

    return account;
  }


  /**
   * 일반 유저 정보를 로그인 객체에 설정
   * @param userInfo
   * @return
   */
  private  Account getAccountFromUserinfo(UserInfo userInfo) {

    Account account = new Account();
    account.setAccountId(userInfo.getUserId());
    account.setAccountPw(userInfo.getUserPw());
    account.setAccountNm(userInfo.getUserNm());
    account.setRoles(userInfo.getRoles());
//    account.setAuthCd(userInfo.getAuthCd());
    account.setUserInfo(userInfo);

    return account;
  }

  /**
   * 가맹점 정보를 로그인 객체에 설정
   * @param mrhstTrmnl
   * @return
   */
  private  Account getAccountFromMrhst(MrhstTrmnl mrhstTrmnl) {

    Account account = new Account();
    account.setAccountId(mrhstTrmnl.getUserId());
    account.setAccountPw(mrhstTrmnl.getUserPw());
    account.setAccountNm(mrhstTrmnl.getTrmnlNm());
//    account.setAuthCd(mrhstTrmnl.getAuthCd());
    account.setRoles(mrhstTrmnl.getRoles());
    account.setMrhstTrmnl(mrhstTrmnl);

    return account;
  }
}
