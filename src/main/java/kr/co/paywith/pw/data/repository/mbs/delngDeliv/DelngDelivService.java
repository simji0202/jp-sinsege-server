package kr.co.paywith.pw.data.repository.mbs.delngDeliv;

import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DelngDelivService {

  @Autowired
  DelngDelivRepository delngDelivRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;


  /**
   * 정보 등록
   */
  @Transactional
  public DelngDeliv create(DelngDeliv delngDeliv) {

    // 데이터베이스 값 갱신
    DelngDeliv newDelngDeliv = this.delngDelivRepository.save(delngDeliv);

    return newDelngDeliv;
  }


  /**
   * 정보 갱신
   */
  @Transactional
  public DelngDeliv update(DelngDelivUpdateDto delngDelivUpdateDto, DelngDeliv existDelngDeliv) {

    // 입력값 대입
    this.modelMapper.map(delngDelivUpdateDto, existDelngDeliv);

    // 데이터베이스 값 갱신
    this.delngDelivRepository.save(existDelngDeliv);

    return existDelngDeliv;
  }

}
