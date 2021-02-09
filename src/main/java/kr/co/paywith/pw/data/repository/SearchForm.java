package kr.co.paywith.pw.data.repository;


import kr.co.paywith.pw.common.NameDescription;


import kr.co.paywith.pw.data.repository.enumeration.CpnSttsType;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class SearchForm {

  // 공통
  @NameDescription("Id")
  private Integer id;


  @NameDescription("Id")
  private String adminId;


  @NameDescription("사용처리 Id")
  private Long useId;

  @NameDescription("삭제/취소 레코드 포함 여부. 삭제만(Y), 삭제제외(N), 전체(null)")
  private String delYn;

  // 게시판

  private String bbsSj;

  // 쿠폰
  /**
   * 쿠폰 상태 코드. 특정 상태 쿠폰 조회
   */
  private CpnSttsType cpnSttsType;

  // 매장 좌석, 직원, 상품 재고
  /**
   * 매장 별..
   */
  private Integer mrhstId;

  // 상품 재고
  /**
   * 상품 별
   */
  private Integer goodsId;

  // BrandPg 등
  /**
   * 브랜드
   */
  private Integer brandId;
}
