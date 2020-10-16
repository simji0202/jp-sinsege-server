package kr.co.paywith.pw.data.repository.admin;



import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.agents.Agents;
import kr.co.paywith.pw.data.repository.partners.Partners;
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
public class AdminDto {

  private Integer id;

  @NameDescription("관리자, 업체, 에이전트 id")
  private String adminId;

  @NameDescription("이름")
  private String adminNm;

  @NameDescription("타입(agent, 업체, 에이전트, 이사업체)")
  @Enumerated(EnumType.STRING)
  private AdminType adminType;

  @NameDescription("비밀번호")
  private String adminPw;

  @NameDescription("이메일(아이디)")
  private String  email;

  @NameDescription("관리자타입")
  @Enumerated(EnumType.STRING)
  private Set<AdminRole> roles;

  @NameDescription("관리자타입")
  private Partners partners;

  @NameDescription("에이젼트 ")
  private Agents agents;
}
