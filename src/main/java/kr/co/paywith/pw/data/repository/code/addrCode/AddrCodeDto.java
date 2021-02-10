package kr.co.paywith.pw.data.repository.code.addrCode;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Id;
import kr.co.paywith.pw.data.repository.enumeration.BbsType;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import lombok.Data;

/**
 * 게시판(게시물)
 */
@Data
public class AddrCodeDto {

  /**
   * 코드
   */
  private String code;

  /**
   * 코드 명
   */
  private String nm;

}

