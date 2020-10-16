package kr.co.paywith.pw.data.repository.admin;

import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AdminUpdateDto {

  private Integer id;

  private String adminId;

  private String adminNm;

  @NameDescription("타입(agent, 업체, 에이전트, 이사업체)")
  @Enumerated(EnumType.STRING)
  private AdminType adminType;

  @NameDescription("전화번호")
  private String phone;

  @NameDescription("관리자타입")
  @Enumerated(EnumType.STRING)
  private Set<AdminRole> roles;

}
