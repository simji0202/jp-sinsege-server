package kr.co.paywith.pw.data.repository.mbs.delngReview;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import kr.co.paywith.pw.common.NameDescription;
import lombok.Data;

@Data
public class DelngReviewUpdateDto {
  @NameDescription("제목")
  @NotNull
  private String sj;

  @NameDescription("내용")
  @NotNull
  private String cn;

  @NameDescription("점수")
  @NotNull
  @Min(0)
  @Max(5)
  private Integer score;

}
