package kr.co.paywith.pw.data.repository.prx.brandPg;

import javax.persistence.ManyToOne;
import kr.co.paywith.pw.data.repository.enumeration.PgType;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandPgDto {

  /**
   * 상점 아이디. PG 업체에서 발급해주는 식별 값
   */
  private String mallId;

  /**
   * 결제 확인, 취소 등에 사용하는 상점 암호(키)
   */
  private String mallKey;

  /**
   * PG 구분. pw-server 내에서 PG 구분을 위해 사용
   */
  private PgType pgType;

  private Brand brand;

}
