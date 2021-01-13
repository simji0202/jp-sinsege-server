package kr.co.paywith.pw.data.repository.mbs.goodsOpt;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.goodsOptMaster.GoodsOptMaster;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsOptDto {

	@NameDescription("식별번호")
	private Integer id;

  private String optTitle;

  private List<GoodsOptMaster> goodsOptMasters = new ArrayList<GoodsOptMaster>();

  /**
   * 다중 선택 여부 ( 라디오버튼 : false , 체크박스 : true )
   */
  private Boolean multiChoiceFl = false;

  /**
   * 필수 여부
   */
  private Boolean requiredFl = true;

  /**
   * 표시 순서
   */
  private Integer sort = 0;

  /**
   * 사용 여부
   */
  private Boolean activeFl = true;

}