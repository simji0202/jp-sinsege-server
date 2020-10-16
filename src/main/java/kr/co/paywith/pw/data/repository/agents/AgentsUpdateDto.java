package kr.co.paywith.pw.data.repository.agents;

import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentsUpdateDto {

  private Integer id;

  @NameDescription("상태")
  private String status;

  @NameDescription("전화번호")
  private String phone;

  @NameDescription("담당자 이름")
  private String managerName;

  @NameDescription("담당자 전화번호")
  private String managerPhone;


}
