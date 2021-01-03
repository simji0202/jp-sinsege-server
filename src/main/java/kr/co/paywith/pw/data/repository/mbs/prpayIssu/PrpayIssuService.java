package kr.co.paywith.pw.data.repository.mbs.prpayIssu;

import kr.co.paywith.pw.component.StringUtil;
import kr.co.paywith.pw.data.repository.mbs.prpay.Prpay;
import kr.co.paywith.pw.data.repository.mbs.prpay.PrpayRepository;
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
  private PrpayRepository prpayRepository;

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
        prpay.setPrpayNm(newPrpayIssu.getPrpayGoods().getPrpayGoodsNm());
        prpay.setPrpayIssu(newPrpayIssu);
        // kms: TODO 브랜드 정책 맞춰서 아래 정보 저장
        // TODO 선불카드 유효기간 저장
        //prpay.setValidDttm();
        // TODO 선불카드 최대 충전 금액
//					prpay.setChrgMaxAmt(newPrpayIssu.amt);
        // TODO 선불카드 충전 금액
//					prpay.setChrgTotAmt();

        if (StringUtils.isEmpty(prpay.getPrpayNo())) {
          // 선불카드 번호 생성. 중복 방지를 위해 prpayIssu를 활용한다
          boolean isEndMakeNo = false;
          do {
            String noRule = "9";
            noRule += StringUtils.leftPad("" + prpayIssu.getId(), 8, "0"); //90000000
            noRule = StringUtils.rightPad(noRule, 15, RandomStringUtils.randomNumeric(12));
            String prpayNo = StringUtil.makeNo(noRule);
            if (prpayRepository.findByPrpayNo(prpayNo).isEmpty()) {
              isEndMakeNo = true;
              prpay.setPrpayNo(prpayNo);
            }
          } while (!isEndMakeNo);
        }

        if (StringUtils.isEmpty(prpay.getPinNum())) { // PIN번호가 없다면
          // PIN 번호 서버에서 생성
          prpay.setPinNum(RandomStringUtils.randomNumeric(6));
        }
        prpayList.add(prpay);
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
