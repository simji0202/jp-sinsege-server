package kr.co.paywith.pw.data.repository.mbs.scoreHist;

import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreHistDto {

    /**
     * 점수
     */
    private Integer scoreAmt;

    /**
     * 회원
     */
    private UserInfo userInfo;

}