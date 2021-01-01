package kr.co.paywith.pw.data.repository.mbs.cd.addr;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * 시도코드
 */
@Data
public class CdAddr1ResponseDto {

  /**
   * 시도 코드
   */
  @Id
  @Column(length = 10, name = "cd")
  private String cd;
  /**
   * 시도 명
   */
  @Column(length = 50, name = "name")
  private String name;

}
