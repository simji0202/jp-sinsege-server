package kr.co.paywith.pw.data.repository.mbs.mrhstDelivAmt;

import kr.co.paywith.pw.data.repository.code.addrCode.AddrCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MrhstDelivAmtDto {

  private Integer mrhstId;

  /**
   * 주소 코드
   */
  private AddrCode addrCode;

  /**
   * 해당 지역 배달비
   */
  private Integer delivAmt;

}
