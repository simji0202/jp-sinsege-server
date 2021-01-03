package kr.co.paywith.pw.data.repository.mbs.prpayIssu;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import kr.co.paywith.pw.data.repository.mbs.prpay.Prpay;
import kr.co.paywith.pw.data.repository.mbs.prpayGoods.PrpayGoods;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrpayIssuDto {

  /**
   * 선불카드 발급 이름
   */
  private String prpayIssuNm;

  /**
   * 선불카드 발급 장수
   */
  private Integer cnt;

  /**
   * 선불카드 종류
   */
  private PrpayGoods prpayGoods;

  /**
   * 발급할 prpay에 일괄로 정할 유효일시
   */
  private ZonedDateTime validDttm;

  /**
   * 발급 선불카드 목록
   */
  private List<Prpay> prpayList = new ArrayList<>();

}