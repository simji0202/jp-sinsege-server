package kr.co.paywith.pw.data.repository.admin;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class AdminAdapter extends User {

  private Admin admin;

  public AdminAdapter(Admin admin) {
    super(admin.getAdminId(), admin.getAdminPw(), authorities(admin.getRoles()));
    this.admin = admin;
  }

  private static Collection<? extends GrantedAuthority> authorities(Set<AdminRole> roles) {
    return roles.stream()
        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
        .collect(Collectors.toSet());
  }

  public Admin getAdmin() {
    return admin;
  }
}
