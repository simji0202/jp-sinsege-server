package kr.co.paywith.pw.data.repository.mbs.delngDeliv;

import java.time.LocalDateTime;
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

  @NameDescription("일련번호")
  private Integer id;

  @NameDescription("발송시각")
  private LocalDateTime outDttm;

  @NameDescription("수령시각")
  private LocalDateTime inDttm;

  @NameDescription("수령자명")
  private String inNm;

  @NameDescription("수령주소")
  private String inAddr;

  @NameDescription("요청사항")
  private String reqCn;


}
