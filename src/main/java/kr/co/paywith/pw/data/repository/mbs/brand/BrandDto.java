package kr.co.paywith.pw.data.repository.mbs.brand;

import java.util.List;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import kr.co.paywith.pw.data.repository.mbs.enumeration.AvailBrandFnCd;
import kr.co.paywith.pw.data.repository.mbs.enumeration.AvailServiceCd;
import kr.co.paywith.pw.data.repository.mbs.enumeration.ChrgSetleMthdCd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandDto {


    private Integer id;
    /**
     * 브랜드 코드
     * 고정 16개 자리를 이용하여 부모 및 자식 브랜드 정의
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
     * 업체에서 사용가능한 기능.
     * <p>
     * 개별 필드로 구분하던 걸 토글할 기능이 많아져서 리스트로 구현
     * <p>
     * TODO 관리자와 그 외 사용이 없어진다면 offGoodsFl, useOrdrFl 필드 삭제
     */
    private List<AvailBrandFnCd> availBrandFnCdList;

    /**
     * 앱(PG)에서 결제 가능한 수단
     */
    private List<ChrgSetleMthdCd> availAppChrgSetleMthdCdList;

    /**
     * 매장에서 결제 가능한 수단
     */
    private List<ChrgSetleMthdCd> availPosChrgSetleMthdCdList;

    // kms: 삭제가능. ROLE 로 대체
//    /**
//     * 관리자에서 브랜드 관리자에게 보일 메뉴 코드 목록
//     */
//    @Enumerated(EnumType.STRING)
//    private List<MenuItemCd> menuItemCdBMstList;

    // kms: 삭제가능. ROLE 로 대체
//    /**
//     * 관리자에서 상점 관리자에게 보일 메뉴 코드 목록
//     */
//    @Enumerated(EnumType.STRING)
//    private List<MenuItemCd> menuItemCdSMstList;

    // kms: 삭제가능. ROLE 로 대체
//    /**
//     * 관리자에서 브랜드 관리자에게 수정 권한을 줄 메뉴 코드 목록
//     */
//    @Enumerated(EnumType.STRING)
//    private List<MenuItemCd> editableMenuItemCdBMstList;

    // kms: 삭제가능. ROLE 로 대체
//    /**
//     * 관리자에서 상점 관리자에게 수정 권한을 줄 메뉴 코드 목록
//     */
//    @Enumerated(EnumType.STRING)
//    private List<MenuItemCd> editableMenuItemCdSMstList;

    // kms: 삭제가능. ROLE 로 대체
    /**
     * 브랜드 내 매장에서 사용가능한 전체 서비스 목록
     * <p>
     * 매장마다 서비스 가능한 목록을 설정할 때 사용한다
     */
    private List<AvailServiceCd> availServiceCdList;

    // kms: 삭제가능. 회원 정보 중 중복 가능한 필드 설정하는 부분이었지만, 아이디는 서비스 전체에서 하나가 되므로..
//    /**
//     * 브랜드 내 매장에서 사용가능한 전체 서비스 목록
//     * <p>
//     * 매장마다 서비스 가능한 목록을 설정할 때 사용한다
//     */
//    @Enumerated(EnumType.STRING)
//    private List<DuplicateAvailFieldCd> duplicateAvailFieldCdList;


    /**
     * 기본적인 브랜드 옵션 설정
     */
    private BrandSetting brandSetting;


    /**
     * 기본적인 브랜드 옵션 설정
     */
    private BrandApp brandApp;

// kms:
//    /**
//     * 기본적인 안드로이드, IOS 앱  옵션 설정
//     */
//    private BrandAuth brandAuth;


    private String envValueMap;

}



