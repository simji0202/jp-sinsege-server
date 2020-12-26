package kr.co.paywith.pw.data.repository.od.userGoodsOptEtc;

import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGoodsOptEtcDto {

    @NameDescription("식별번호")
    private Integer id;


}