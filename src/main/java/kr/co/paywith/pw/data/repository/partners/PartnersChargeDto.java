package kr.co.paywith.pw.data.repository.partners;


import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnersChargeDto {

  @NameDescription("업체번호")
  private Integer id;



  @NameDescription("충전금액")
  private int chrgAmt;

  @NameDescription("내용")
  private String comment;
}

