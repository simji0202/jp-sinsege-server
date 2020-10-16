package kr.co.paywith.pw.data.repository.partners;


import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.admin.AdminRepository;
import kr.co.paywith.pw.data.repository.admin.AdminRole;
import kr.co.paywith.pw.data.repository.admin.AdminType;
import kr.co.paywith.pw.mapper.StatisticsMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class PartnersService {

  @Autowired
  PartnersRepository partnersRepository;

  @Autowired
  AdminRepository adminRepository;


  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  CompanyRepository companyRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  StatisticsMapper statisticsMapper;



  @Transactional
  public Partners createPartners(PartnersDto partnersDto, Admin currentUser) {


    Partners partners = modelMapper.map(partnersDto, Partners.class);


    if ( currentUser != null ) {
      partners.setCreateBy(currentUser.getAdminNm());
      partners.setUpdateBy(currentUser.getAdminNm());
    }


    if ( partnersDto != null && partnersDto.getCompany() != null ) {
      Company company = this.companyRepository.save(partnersDto.getCompany());

      partners.setCompany(company);
    }

    Partners newPartners = this.partnersRepository.save(partners);

    if ( partners != null && partners.getAdminId() != null ) {

      Admin admin = new Admin();

      admin.setAdminId(partnersDto.getAdminId());
      admin.setAdminNm(partnersDto.getCompany().getCompanyNm());
      admin.setAdminType(AdminType.PARTNER);
      admin.setRoles(Set.of(AdminRole.PARTNER));
      if ( currentUser != null ) {
        admin.setCreateBy(currentUser.getAdminNm());
        admin.setUpdateBy(currentUser.getAdminNm());
      }

      admin.setAdminPw(this.passwordEncoder.encode(partnersDto.getPassword()));
      admin.setPartners(newPartners);

      this.adminRepository.save(admin);

    }

    return newPartners;
  }

//  /**
//   * @return
//   */
//  public List<PartnersListExcel> getPartnersListExcel(SearchForm searchForm) {
//
//    // 입출금 이력 통계
//    List<PartnersListExcel> partnersListExcels = this.statisticsMapper.excelPartnersList(searchForm);
//
//    return partnersListExcels;
//  }

  @Transactional
  public void updateName(Integer id, String name) {

    // 업체명 변경
    partnersRepository.updatePartnersName(id, name);


  }




}
