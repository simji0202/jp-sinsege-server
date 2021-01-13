package kr.co.paywith.pw.data.repository.mbs.cpnMaster;


import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CpnMasterService {

  @Autowired
  private CpnMasterRepository cpnMasterRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  /**
   * 정보 등록
   */
  @Transactional
  public CpnMaster create(CpnMaster cpnMaster) {

    // 데이터베이스 값 갱신
    CpnMaster newCpnMaster = this.cpnMasterRepository.save(cpnMaster);

    return newCpnMaster;
//    return cpnMasterRepository.save(newCpnMaster);
  }


  /**
   * 정보 갱신
   */
  @Transactional
  public CpnMaster update(CpnMasterUpdateDto cpnMasterUpdateDto, CpnMaster existCpnMaster) {

    // 입력값 대입
    this.modelMapper.map(cpnMasterUpdateDto, existCpnMaster);

//    this.cpnMasterRepository.save(existCpnMaster);

    // 데이터베이스 값 갱신
    return cpnMasterRepository.save(existCpnMaster);
  }

}
