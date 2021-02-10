package kr.co.paywith.pw.data.repository.mbs.delngDeliv;

import java.time.LocalDateTime;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DelngDelivDto {

  @NameDescription("배달비")
  private Integer delivAmt;

  @NameDescription("발송시각")
  private LocalDateTime sendDttm;

  @NameDescription("수령시각")
  private LocalDateTime rcvDttm;

  @NameDescription("수령자명")
  private String rcvNm;

  @NameDescription("수령주소코드")
  private String rcvAddrCodeCd;

  @NameDescription("수령주소")
  private String rcvAddr;

  @NameDescription("요청사항")
  private String reqCn;


}
