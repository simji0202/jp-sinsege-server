package kr.co.paywith.pw.data.repository.user.userCard;

import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCardDto {

  @NameDescription("식별번호")
  private Integer id;


}