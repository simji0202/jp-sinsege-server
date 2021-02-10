package kr.co.paywith.pw.data.repository.mbs.delngDeliv;

import java.time.LocalDateTime;
import kr.co.paywith.pw.common.NameDescription;
import lombok.Data;

@Data
public class DelngDelivUpdateDto {
  @NameDescription("발송시각")
  private LocalDateTime sendDttm;

  @NameDescription("수령시각")
  private LocalDateTime rcvDttm;

  @NameDescription("수령자명")
  private String rcvNm;

  @NameDescription("수령주소")
  private String rcvAddr;

  @NameDescription("요청사항")
  private String reqCn;


}
