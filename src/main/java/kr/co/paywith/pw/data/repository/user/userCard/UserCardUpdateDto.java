package kr.co.paywith.pw.data.repository.user.userCard;

import kr.co.paywith.pw.common.NameDescription;
import lombok.Data;

@Data
public class UserCardUpdateDto {

	@NameDescription("식별번호")
	private Integer id;


}