package kr.co.paywith.pw.data.repository.mbs.chrg;

import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChrgDto {

    @NameDescription("식별번호")
    private Integer id;

    @NameDescription("테스트 이름 ")
    private String testNm;

}