package kr.co.paywith.pw.data.repository.od.seat;

import kr.co.paywith.pw.common.NameDescription;
import lombok.Data;

@Data
public class SeatUpdateDto {

    @NameDescription("식별번호")
    private Integer id;


}