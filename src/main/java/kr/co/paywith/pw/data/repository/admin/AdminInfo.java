package kr.co.paywith.pw.data.repository.admin;

import java.util.Set;

public interface AdminInfo {

  Integer getId();

  String getAdminId();

  String getAdminNm();

  AdminType getAdminType();

  //
  String getAdminPw();

  //
  Set<AdminRole> getRoles();

}
