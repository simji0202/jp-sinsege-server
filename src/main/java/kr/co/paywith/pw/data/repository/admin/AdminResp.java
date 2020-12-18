package kr.co.paywith.pw.data.repository.admin;

import java.util.Set;

public interface AdminResp {

  Integer getId();

  String getAdminId();

  String getAdminNm();

  //
  String getAdminPw();

  //
  Set<AdminRole> getRoles();

}
