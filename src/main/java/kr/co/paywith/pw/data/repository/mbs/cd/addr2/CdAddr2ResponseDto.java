package kr.co.paywith.pw.data.repository.mbs.cd.addr2;

import kr.co.paywith.pw.data.repository.mbs.cd.addr.CdAddr1ResponseDto;
import lombok.Data;

/**
 * 시군구 코드
 */
@Data
public class CdAddr2ResponseDto {

  private String cd;

  /**
   * 시군구 명
   */
  private String name;

  /**
   * 시도 코드
   */
  private CdAddr1ResponseDto cdAddr1;

}
