package kr.co.paywith.pw.data.repository.mbs.delng;


import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DelngService {

  @Autowired
  private DelngRepository delngrRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  /**
   * 정보 등록
   */
  @Transactional
  public Delng create(Delng delng) {

    // 데이터베이스 값 갱신
    Delng newDelng = this.delngrRepository.save(delng);

    return newDelng;
  }


//  /**
//   * 정보 갱신
//   */
//  @Transactional
//  public Delng update(DelngUpdateDto delngUpdateDto, Delng existDelng) {
//
//    // 입력값 대입
//    this.modelMapper.map(delngUpdateDto, existDelng);
//
//    // 데이터베이스 값 갱신
//    this.delngrRepository.save(existDelng);
//
//    return existDelng;
//  }

}
