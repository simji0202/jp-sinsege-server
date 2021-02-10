package kr.co.paywith.pw.data.repository;


import java.time.LocalDateTime;
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

  /**
   * 기간 검색 시작 일시
   */
  private LocalDateTime rangeStartDttm;

  /**
   * 기간 검색 종료 일시
   */
  private LocalDateTime rangeEndDttm;

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

  /**
   * 직원 별..
   */
  private Integer staffId;

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

  // 코드 api 공통 (주소코드, ...)
  /**
   * 코드
   */
  private String cd;

  /**
   * 시작 문자열
   */
  private String s;

  /**
   * 종료 문자열
   */
  private String e;

  /**
   * 코드 길이
   */
  private Integer length;
}
