package kr.co.paywith.pw.data.repository.admin;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class AdminService  {

  @Autowired
  AdminRepository adminRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  /**
   * 정보 등록
   */
  @Transactional
  public Admin create(Admin admin) {

    admin.setAdminPw(this.passwordEncoder.encode(admin.getAdminPw()));
    return this.adminRepository.save(admin);
  }

  /**
   *  정보 갱신
   * @return
   */
  @Transactional
  public Admin update(AdminUpdateDto adminUpdateDto, Admin admin) {

    // 입력값 대입
    this.modelMapper.map(adminUpdateDto, admin);
    return this.adminRepository.save(admin);
  }



  /**
   * 관리자 암호 변경
   * @return
   */
  @Transactional
  public Admin updateAdminPw(Admin admin) {

    // 패스워드 설정
    admin.setAdminPw(this.passwordEncoder.encode(admin.getAdminPw()));
    return this.adminRepository.save(admin);
  }

}
