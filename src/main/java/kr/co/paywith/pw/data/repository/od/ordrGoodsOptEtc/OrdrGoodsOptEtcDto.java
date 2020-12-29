package kr.co.paywith.pw.data.repository.od.ordrGoodsOptEtc;

import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdrGoodsOptEtcDto {

    @NameDescription("식별번호")
    private Integer id;


}