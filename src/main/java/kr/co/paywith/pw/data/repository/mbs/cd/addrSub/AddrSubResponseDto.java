package kr.co.paywith.pw.data.repository.mbs.cd.addrSub;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * 시군구 코드
 */
@Data
public class AddrSubResponseDto {

  @Id
  @Column(length = 10, name = "cd")
  private String cd;

  /**
   * 시군구 명
   */
  @Column(length = 50, name = "name")
  private String name;

  /**
   * 시도 코드
   */
  @Column
  private String addrCd;

}
