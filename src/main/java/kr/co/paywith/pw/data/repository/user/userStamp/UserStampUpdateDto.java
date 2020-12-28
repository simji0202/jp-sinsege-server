package kr.co.paywith.pw.data.repository.user.userStamp;

import kr.co.paywith.pw.common.NameDescription;
import lombok.Data;

@Data
public class UserStampUpdateDto {

	@NameDescription("식별번호")
	private Integer id;


}