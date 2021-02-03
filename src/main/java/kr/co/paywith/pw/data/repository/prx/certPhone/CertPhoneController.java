package kr.co.paywith.pw.data.repository.prx.certPhone;

import com.popbill.api.MessageService;
import com.popbill.api.PopbillException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.Valid;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.brand.BrandRepository;
import kr.co.paywith.pw.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cert/phone")
@Slf4j
public class CertPhoneController {

  /**
   * 인증문자 길이
   */
  private final int CERT_LENGTH = 4;

  /**
   * 인증 유효 시간(초)
   */
  private final int CERT_VALID_SECOND = 300;

  /**
   *
   */
  private final String CERT_MSG = "인증문자 ${CERT} 를 입력해주세요";

  @Autowired
  private CertPhoneRepository certPhoneRepository;

  @Autowired
  private BrandRepository brandRepository;

  @Autowired
  private MessageService messageService;

  /**
   * 인증번호 요청
   *
   * @param certRequestModel
   * @return
   */
  @PostMapping(value = "/req")
  @Transactional
  public Map requestNewCert(
      @RequestBody @Valid CertRequestModel certRequestModel
  ) {
    String brandCd = certRequestModel.getBrandCd();
    String corpNo = certRequestModel.getCorpNo();

    // 업체정보 제대로 입력 안하면 걸러짐
    Brand brand = brandRepository.findByBrandCdAndBrandSetting_CorpNo(brandCd, corpNo).get();
    if (brand.getBrandSetting() == null ||
        brand.getBrandSetting().getMsgId() == null
    ) {
      throw new CustomException("9999", "브랜드 정보 없음");
    }

    corpNo = corpNo.replaceAll("-", "");

    String mobileNum = certRequestModel.getMobileNum();

    // 유효한 번호인지 체크
    Pattern p = Pattern.compile("[\\+]{0,1}[\\-0-9]{9,15}$");
    Matcher m = p.matcher(mobileNum);
    if (!m.find()) {
      throw new CustomException("9999", "휴대폰 번호 형식 오류");
    }
    mobileNum = mobileNum.replace("-", "");
    if (mobileNum.length() < 8 || mobileNum.length() > 14) { // 번호가 너무 짧거나 길면
      // 오류
      throw new CustomException("9999", "휴대폰 번호 형식 오류");
    }

    // 문자 발송 요청 생성 후 팝빌에 발송 요청
    String senderNumber = brand.getBrandSetting().getMsgSenderTel();

    String certNum = getRandNum(CERT_LENGTH);

    String content = CERT_MSG.replace("${CERT}", certNum);

    // 기존 인증 요청이 있다면 유효시간 갱신하고, 아니면 새로 생성
    CertPhone certPhone =
        certPhoneRepository
            .findByMobileNumAndBrandIdAndCheckValidDttmIsNull(mobileNum, brand.getId());
    if (certPhone == null) {
      certPhone = new CertPhone();
      certPhone.setBrandId(brand.getId());
      certPhone.setMobileNum(mobileNum);
    }
    certPhone.setInputValidDttm(ZonedDateTime.now().plusSeconds(CERT_VALID_SECOND));
    certPhone.setCertNum(certNum);
    certPhoneRepository.save(certPhone);

    // TODO 중복요청 방지 필요?

    String userId = brand.getBrandSetting().getMsgId();

    Date reserveDT = null;
    Boolean adsYN = false;
    String receiverName = certRequestModel.getNm() != null ? certRequestModel.getNm()
        : certRequestModel.getMobileNum();
    // 중복 사업자 등록 번호로 여러 서비스를 할 때 앞부분이 실제 번호이므로 분리
    // (같은 사업자가 여러 브랜드를 등록한 경우 사업자 번호로 식별 불가능하므로 _ 를 통해 _1, _2 와 같이 사용할 수 있게함)
    corpNo = corpNo.split("_")[0];
    String requestNum = "";

    String result = null; // 팝빌 응답값
    try {
      result = messageService.sendSMS(corpNo, senderNumber, mobileNum,
          receiverName, content, reserveDT, adsYN, userId, requestNum);

      certPhone.setPopbillResponse(result);
    } catch (PopbillException e) {
      log.warn("팝빌 문자 전송 오류 >>> {}, {}", certPhone, e.getMessage());
      // "요청한 고유번호에 해당하는 사용자정보가 존재하지 않습니다" 등...
      certPhone.setPopbillResponse(e.getMessage());
    }
    certPhoneRepository.save(certPhone);

    // TODO 팝빌 응답값을 구체적으로 구분해야 하는지 확인
    boolean isSendSuccess = result != null;

    return Map.of(
        "result", isSendSuccess,
        "certId", certPhone.getId(),
        "valid", CERT_VALID_SECOND
    );
  }

  /**
   * 인증번호 확인
   *
   * @param certCheckModel
   * @return
   */
  @PostMapping(value = "/chk")
  @Transactional
  public Map checkCert(
      @RequestBody @Valid CertCheckModel certCheckModel
  ) {
    String brandCd = certCheckModel.getBrandCd();
    String corpNo = certCheckModel.getCorpNo();

    // 업체정보 제대로 입력 안하면 걸러짐
    Brand brand = brandRepository.findByBrandCdAndBrandSetting_CorpNo(brandCd, corpNo).get();
    if (brand == null ||
        brand.getBrandSetting() == null ||
        brand.getBrandSetting().getMsgId() == null
    ) {
      throw new CustomException("9999", "브랜드 정보 없음");
    }

    String mobileNum = certCheckModel.getMobileNum();

    // 유효한 번호인지 체크
    Pattern p = Pattern.compile("[\\+]{0,1}[\\-0-9]{9,15}$");
    Matcher m = p.matcher(mobileNum);
    if (!m.find()) {
      throw new CustomException("9999", "휴대폰 번호 형식 오류");
    }
    mobileNum = mobileNum.replace("-", "");

    CertPhone certPhone = certPhoneRepository.findById(certCheckModel.getCertPhoneId()).get();

    if (!certPhone.getMobileNum().equals(certCheckModel.getMobileNum())) {
      log.info("유효하지 않은 인증 정보로 검증 시도 >>> {}", certCheckModel);
      return Map.of(
          "result", false
      );
    }

    if (!certPhone.getCertNum().equals(certCheckModel.getCertNum())) {
      // 인증번호를 잘못 입력했다면 오류
      return Map.of(
          "result", false
      );
    }

    if (certPhone.getCheckValidDttm() != null && certPhone.getCheckValidDttm()
        .isBefore(ZonedDateTime.now())) {
      // 인증 유효시간 만료
      return Map.of(
          "result", false
      );
    }
    if (certPhone.getCheckValidDttm() == null || certCheckModel.getExtendFl()) {
      // 최초 인증이라 유효시간(checkValidDttm) 초기 설정. 혹은 연장
      certPhone.setCheckValidDttm(ZonedDateTime.now().plusSeconds(CERT_VALID_SECOND));
      certPhone.setInputValidDttm(ZonedDateTime.now());
    }

    return Map.of(
        "result", true,
        "certId", certPhone.getId(),
        "valid", ChronoUnit.SECONDS.between(ZonedDateTime.now(), certPhone.getCheckValidDttm())
    );
  }

  private String getRandNum(int digit) {
    Random rand = new Random();
    StringBuilder numStr = new StringBuilder();
    for (int i = 0; i < digit; i++) {
      numStr.append(rand.nextInt(10));
    }
    return numStr.toString();
  }
}
