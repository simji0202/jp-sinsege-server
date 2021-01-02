package kr.co.paywith.pw.data.repository.mbs.stamp;

import java.time.ZonedDateTime;
import kr.co.paywith.pw.data.repository.enumeration.StampSttsTypeCd;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StampService {

  @Autowired
  StampRepository stampRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;


  /**
   * 정보 등록
   */
  @Transactional
  public Stamp create(Stamp stamp) {

    // 데이터베이스 값 갱신
    Stamp newStamp = this.stampRepository.save(stamp);

    return newStamp;
  }


  /**
   * 정보 갱신
   */
  @Transactional
  public Stamp update(StampUpdateDto stampUpdateDto, Stamp existStamp) {

    // 입력값 대입
    this.modelMapper.map(stampUpdateDto, existStamp);

    // 데이터베이스 값 갱신
    this.stampRepository.save(existStamp);

    return existStamp;
  }

  /**
   * 스탬프 삭제. 적립 상태 스탬프를 모두 취소한다. DB 효율화를 위해 바로 삭제.
   */
  @Transactional
  public void delete(Stamp existStamp) {
    // 스탬프 삭제 처리. 이전 validator에서 사용가능 스탬프인지 모두 확인했어야 함
    stampRepository.delete(existStamp);
  }

}
