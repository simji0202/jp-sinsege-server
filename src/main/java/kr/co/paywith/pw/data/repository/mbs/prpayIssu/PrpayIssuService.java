package kr.co.paywith.pw.data.repository.mbs.prpayIssu;

import kr.co.paywith.pw.component.StringUtil;
import kr.co.paywith.pw.data.repository.mbs.prpay.Prpay;
import kr.co.paywith.pw.data.repository.mbs.prpay.PrpayRepository;
import kr.co.paywith.pw.data.repository.mbs.prpay.PrpayService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PrpayIssuService {

  @Autowired
  PrpayIssuRepository prpayIssuRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  private PrpayService prpayService;

  /**
   * 정보 등록
   */
  @Transactional
  public PrpayIssu create(PrpayIssu prpayIssu) {

    // 데이터베이스 값 갱신
    PrpayIssu newPrpayIssu = this.prpayIssuRepository.save(prpayIssu);

    // 선불카드 생성
    if (newPrpayIssu.getPrpayList().size() < newPrpayIssu.getCnt()) {
      // 요청에 prpay 정보가 없다면 추가로 생성.(클라이언트에서 직접 정보를 적지 않은 선불카드)
      List<Prpay> prpayList = newPrpayIssu.getPrpayList();
      for (int i = 0; i < newPrpayIssu.getCnt() - newPrpayIssu.getPrpayList().size(); i++) {
        Prpay prpay = new Prpay();
        prpay.setPrpayIssu(newPrpayIssu);
      }

      for (Prpay prpay : newPrpayIssu.getPrpayList()) {
        // 선불카드 정보 저장
        if (prpay.getPrpayGoods() == null) {
          prpay.setPrpayGoods(prpayIssu.getPrpayGoods());
        }

        if (prpay.getValidDttm() == null) {
          prpay.setValidDttm(prpayIssu.getValidDttm());
        }
        prpay.setPrpayIssu(newPrpayIssu);

        prpayService.create(prpay);
      }
    }
    newPrpayIssu.setCnt(newPrpayIssu.getPrpayList().size());

    return newPrpayIssu;
  }


  /**
   * 정보 갱신
   */
  @Transactional
  public PrpayIssu update(PrpayIssuUpdateDto prpayIssuUpdateDto, PrpayIssu existPrpayIssu) {

    // 입력값 대입
    this.modelMapper.map(prpayIssuUpdateDto, existPrpayIssu);

    // 데이터베이스 값 갱신
    this.prpayIssuRepository.save(existPrpayIssu);

    return existPrpayIssu;
  }

}
