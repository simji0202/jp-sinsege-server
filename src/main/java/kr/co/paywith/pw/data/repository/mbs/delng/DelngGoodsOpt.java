package kr.co.paywith.pw.data.repository.mbs.delng;

import kr.co.paywith.pw.data.repository.mbs.goodsOptMaster.GoodsOptMaster;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class DelngGoodsOpt {


    /**
     * 옵션 이름
     */
    private String optTitle;

    /**
     * 다중 선택 여부 ( 라디오버튼 : false , 체크박스 : true )
     */
    private Boolean multiChoiceFl = false;

    /**
     * 필수 여부
     */
    private Boolean needFl = true;


    private List<DelngGoodsOptMaster> goodsOptMasterList = new ArrayList<>();


}
