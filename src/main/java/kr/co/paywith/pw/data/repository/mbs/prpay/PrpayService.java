package kr.co.paywith.pw.data.repository.mbs.prpay;

import kr.co.paywith.pw.component.StringUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PrpayService {

  @Autowired
  PrpayRepository prpayRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  /**
   * 정보 등록
   */
  @Transactional
  public Prpay create(Prpay prpay) {

    prpay.setPrpayNm(prpay.getPrpayGoods().getPrpayGoodsNm());

    // kms: TODO 브랜드 정책 맞춰서 아래 정보 저장
    // TODO 선불카드 유효기간 저장
    //prpay.setValidDttm();
    // TODO 선불카드 최대 충전 금액
    // prpay.setChrgMaxAmt(newPrpayIssu.amt);
    // TODO 선불카드 충전 금액
    //prpay.setChrgTotAmt();

//    if (StringUtils.isEmpty(prpay.getPrpayNo())) {
//      // 선불카드 번호 생성. 중복 방지를 위해 prpayIssu를 활용한다
//      boolean isEndMakeNo = false;
//      do {
//        String noRule = "9";
//        noRule += StringUtils.leftPad("" + prpay.getPrpayIssu().getId(), 8, "0"); //90000000
//        noRule = StringUtils.rightPad(noRule, 15, RandomStringUtils.randomNumeric(12));
//        String prpayNo = StringUtil.makeNo(noRule);
//        if (prpayRepository.findByPrpayNo(prpayNo).isEmpty()) {
//          isEndMakeNo = true;
//          prpay.setPrpayNo(prpayNo);
//        }
//      } while (!isEndMakeNo);
//    }

    if (StringUtils.isEmpty(prpay.getPinNum())) { // PIN번호가 없다면
      // PIN 번호 서버에서 생성
      prpay.setPinNum(RandomStringUtils.randomNumeric(6));
    }

    // 데이터베이스 값 갱신
    Prpay newPrpay = this.prpayRepository.save(prpay);

    return newPrpay;
  }


  /**
   * 정보 갱신
   */
  @Transactional
  public Prpay update(PrpayUpdateDto prpayUpdateDto, Prpay existPrpay) {

    // 입력값 대입
    this.modelMapper.map(prpayUpdateDto, existPrpay);

    // 데이터베이스 값 갱신
    this.prpayRepository.save(existPrpay);

    return existPrpay;
  }

}
