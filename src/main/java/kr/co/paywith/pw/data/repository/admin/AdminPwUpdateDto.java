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

public class AdminPwUpdateDto {

  @NameDescription("비밀번호")
  private String adminPw;

}
