package kr.co.paywith.pw.data.repository.mbs.mrhstDelivAmt;

import kr.co.paywith.pw.common.NameDescription;
import lombok.Data;

@Data
public class MrhstDelivAmtUpdateDto {

  @NameDescription("배달비")
  private Integer delivAmt;


}
