package kr.co.paywith.pw.data.repository.mbs.cd.addr3;

import kr.co.paywith.pw.data.repository.mbs.cd.addr2.CdAddr2ResponseDto;
import lombok.Data;

/**
 * 시군구 코드
 */
@Data
public class CdAddr3ResponseDto {

  private String cd;

  /**
   * 시군구 명
   */
  private String name;

  /**
   * 시도 코드
   */
  private CdAddr2ResponseDto cdAddr2;

}
