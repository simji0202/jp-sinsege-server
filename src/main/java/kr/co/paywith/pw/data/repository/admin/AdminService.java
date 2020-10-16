package kr.co.paywith.pw.data.repository.admin;



import kr.co.paywith.pw.data.repository.agents.Agents;
import kr.co.paywith.pw.data.repository.agents.AgentsRepository;
import kr.co.paywith.pw.data.repository.partners.Partners;
import kr.co.paywith.pw.data.repository.partners.PartnersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService implements UserDetailsService {


  @Autowired
  AdminRepository adminRepository;

  @Autowired
  PartnersRepository partnersRepository;

  @Autowired
  AgentsRepository agentsRepository;

  @Autowired
  PasswordEncoder passwordEncoder;


  /**
   * @return
   */
  @Transactional
  public Admin saveAdmin(Admin admin) {


    if ( admin.getAdminType() != null ) {

      if (admin.getAdminType().equals(AdminType.PARTNER) && admin.getPartners() != null) {

        //  여행사(파트너_회사)
        Partners partners = admin.getPartners();

        partners.setAdminId(admin.getAdminId());

        if (admin != null) {
          partners.setUpdateBy(admin.getAdminNm());
          partners.setCreateBy(admin.getAdminNm());
        }

        partnersRepository.save(partners);

      } else if (admin.getAdminType().equals(AdminType.BUS_ADMIN) && admin.getAgents() != null) {

        //    버스 업체
        Agents agents = admin.getAgents();

        agents.setAdminId(admin.getAdminId());
        agents.setName(admin.getAdminNm());

        if (admin != null) {
          agents.setUpdateBy(admin.getAdminNm());
          agents.setCreateBy(admin.getAdminNm());
        }
        agentsRepository.save(agents);
      }
    }

    admin.setAdminPw(this.passwordEncoder.encode(admin.getAdminPw()));

    return this.adminRepository.save(admin);
  }







  /**
   * @return
   */
  @Transactional
  public Admin updateAdminPw(Admin admin) {


    admin.setAdminPw(this.passwordEncoder.encode(admin.getAdminPw()));

    return this.adminRepository.save(admin);
  }



  /**
   * @return
   * @throws UsernameNotFoundException
   */
  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

    Admin admin = adminRepository.findByAdminId(userId)
        .orElseThrow(() -> new UsernameNotFoundException(userId));

    return new AdminAdapter(admin);
  }


  private Collection<? extends GrantedAuthority> authorities(Set<AdminRole> roles) {
    return roles.stream()
        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
        .collect(Collectors.toSet());
  }
}
