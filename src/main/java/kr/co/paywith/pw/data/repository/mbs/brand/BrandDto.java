package kr.co.paywith.pw.data.repository.mbs.brand;

import java.util.List;
import kr.co.paywith.pw.data.repository.enumeration.AvailServiceType;
import kr.co.paywith.pw.data.repository.enumeration.BrandFnType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandDto {

  /**
   * 브랜드 코드 고정 16개 자리를 이용하여 부모 및 자식 브랜드 정의
   */
  private String brandCd;

  /**
   * 브랜드 명
   */
  private String brandNm;

  /**
   * 브랜드 사용 여부
   */
  private Boolean activeFl;

  /**
   * 서버사이드에서 관리할 브랜드 이미지
   */
  private String imgUrl;

  /**
   * 서버사이드에서 관리할 브랜드 로고 이미지
   */
  private String logoImgUrl;

  /**
   * 업체에서 사용가능한 기능.
   * <p>
   * 개별 필드로 구분하던 걸 토글할 기능이 많아져서 리스트로 구현
   * <p>
   * TODO 관리자와 그 외 사용이 없어진다면 offGoodsFl, useOrdrFl 필드 삭제
   */
  private List<BrandFnType> brandFnTypeList;

//  /**
//   * 앱(PG)에서 결제 가능한 수단
//   */
//  private List<ChrgSetleMthdCd> availAppChrgSetleMthdCdList;
//
//  /**
//   * 매장에서 결제 가능한 수단
//   */
//  private List<ChrgSetleMthdCd> availPosChrgSetleMthdCdList;

  /**
   * 브랜드 내 매장에서 사용가능한 전체 서비스 목록
   * <p>
   * 매장마다 서비스 가능한 목록을 설정할 때 사용한다
   */
  private List<AvailServiceType> availServiceTypeList;

  /**
   * 기본적인 브랜드 옵션 설정
   */
  private BrandSetting brandSetting;

  private String envValueMap;

}



