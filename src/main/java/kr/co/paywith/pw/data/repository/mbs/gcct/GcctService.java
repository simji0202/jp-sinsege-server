package kr.co.paywith.pw.data.repository.mbs.gcct;


import java.time.ZonedDateTime;
import javax.transaction.Transactional;
import kr.co.paywith.pw.component.StringUtil;
import kr.co.paywith.pw.data.repository.user.user.UserInfoRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class GcctService {

  @Autowired
  private GcctRepository gcctRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  private UserInfoRepository userInfoRepository;

  /**
   * 정보 등록
   */
  @Transactional
  public Gcct create(Gcct gcct) {
    // 데이터베이스 값 갱신
    Gcct newGcct = this.gcctRepository.save(gcct);

    // 상품권 번호 생성. 중복 방지를 위해 먼저 저장한 Gcct.id 를 이용한다
    newGcct.setGcctNo(getGcctNo(newGcct));

    this.gcctRepository.save(gcct);
    return newGcct;
  }


  /**
   * 정보 갱신
   */
  @Transactional
  public Gcct update(GcctUpdateDto gcctUpdateDto, Gcct existGcct) {

    // 입력값 대입
    this.modelMapper.map(gcctUpdateDto, existGcct);

    // 데이터베이스 값 갱신
    this.gcctRepository.save(existGcct);

    return existGcct;
  }


  /**
   * 상품권 취소. 상품권을 구매하는 데 사용한 결제는 가능한 경우 환불처리한다
   *
   * @param gcct 취소할 상품권
   */
  @Transactional
  public void delete(Gcct gcct) {
    gcct.setCancelRegDttm(ZonedDateTime.now());
    
    gcctRepository.save(gcct);

    // TODO gcct.pay 환불처리
  }

  /**
   * 상품권 번호를 생성한다. 상품권 번호는 중복을 방지하기 위해 gcctIssu 생성 이후에 진행하며,
   * 사용하지 않고 만료되는 상품권에 할당되는 번호 낭비를 줄이기 위해, 회원이 상품권 최초 열람시에 생성
   *
   * @param gcct 번호발급이 되지 않은 상품권. gcctIssu가 있어야 한다. gcctNo가 없어야 하지만, 혹시 있다면 gcctNo를 그대로 리턴한다.
   * @return 새로 생성, 혹은 기존 gcct에 있던 상품권 번호
   */
  public String getGcctNo(Gcct gcct) {
    String gcctNo = null;
    // 상품권 번호 생성. 중복 방지를 위해 gcctIssu를 활용한다
    do {
      String noRule = "3";
      noRule += StringUtils.leftPad("" + gcct.getId(), 8, "0"); //90000000
      noRule = StringUtils.rightPad(noRule, 15, RandomStringUtils.randomNumeric(12));
      String gcctNo2 = StringUtil.makeNo(noRule);
      if (gcctRepository.findByGcctNo(gcctNo2).isEmpty()) {
        gcctNo = gcctNo2;
      }
    } while (gcctNo != null);
    return gcctNo;
  }
}
