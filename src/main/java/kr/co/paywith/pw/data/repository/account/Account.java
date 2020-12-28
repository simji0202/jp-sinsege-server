package kr.co.paywith.pw.data.repository.account;

import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.admin.AdminRole;
import kr.co.paywith.pw.data.repository.enumeration.AuthCd;
import kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl.MrhstTrmnl;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter

public  class Account {

  private Integer id;

  /**
   *  로그인 아이디
   */
  private String accountId;

  /**
   *  로그인 패스워드
   */
  private String accountPw;

  /**
   * 로그인 유저 이름
   */
  private String accountNm;

  /**
   * 관리자 객체
   */
  private Admin admin;

  /**
   * 일반유저
   */
  private UserInfo userInfo;

  /**
   * 단말기 번호
   */
  private String trmnlNo;



  /**
   * 가맹점 유저
   */
  private MrhstTrmnl mrhstTrmnl;

  /**  권한 코드  */
  @Enumerated(EnumType.STRING)
  private AuthCd authCd;

  /**
   * 유저 권한
   */
  @Enumerated(EnumType.STRING)
  private Set<AdminRole> roles;

}
