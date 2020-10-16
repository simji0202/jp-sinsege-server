package kr.co.paywith.pw.data.repository.agents;

import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.admin.AdminRepository;
import kr.co.paywith.pw.data.repository.admin.AdminRole;
import kr.co.paywith.pw.data.repository.admin.AdminType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class AgentsService {

  @Autowired
  AgentsRepository agentsRepository;

  @Autowired
  AdminRepository adminRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  ModelMapper modelMapper;


  @Transactional
  public Agents createAgents(AgentsDto agentsDto, Admin currentUser) {

    Agents agents = modelMapper.map(agentsDto, Agents.class);

    if ( currentUser != null ) {
      agents.setCreateBy(currentUser.getAdminNm());
      agents.setUpdateBy(currentUser.getAdminNm());
    }

    Agents newAgents = this.agentsRepository.save(agents);

    if ( agents != null && agents.getAdminId() != null ) {

      Admin admin = new Admin();

      admin.setAdminId(agentsDto.getAdminId());
      admin.setAdminNm(agentsDto.getManagerName());
      admin.setAdminType(AdminType.BUS_ADMIN);
      admin.setRoles(Set.of(AdminRole.BUS_ADMIN));
      if ( currentUser != null ) {
        admin.setCreateBy(currentUser.getAdminNm());
        admin.setUpdateBy(currentUser.getAdminNm());
      }

      admin.setAdminPw(this.passwordEncoder.encode(agentsDto.getPassword()));
      admin.setAgents(newAgents);

      this.adminRepository.save(admin);
    }

    return newAgents;
  }

}
