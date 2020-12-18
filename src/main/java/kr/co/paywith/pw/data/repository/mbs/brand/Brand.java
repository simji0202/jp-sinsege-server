package kr.co.paywith.pw.data.repository.mbs.brand;


import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.enumeration.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 브랜드
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Brand implements Serializable {

  /**
   * 브랜드 일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /**
   * 브랜드 코드
   * 고정 16개 자리를 이용하여 부모 및 자식 브랜드 정의
   *
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
   *
   * 개별 필드로 구분하던 걸 토글할 기능이 많아져서 리스트로 구현
   *
   * TODO 관리자와 그 외 사용이 없어진다면 offGoodsFl, useOrdrFl 필드 삭제
   */
  @Column(length = 10)
  @ElementCollection(fetch = FetchType.LAZY)
  @Enumerated(EnumType.STRING)
  private List<AvailBrandFnCd> availBrandFnCdList  = new ArrayList<>();

  /**
   * 앱(PG)에서 결제 가능한 수단
   */
  @Column(length = 10)
  @ElementCollection(fetch = FetchType.LAZY)
  @Enumerated(EnumType.STRING)
  private List<ChrgSetleMthdCd> availAppChrgSetleMthdCdList  = new ArrayList<>();

  /**
   * 매장에서 결제 가능한 수단
   */
  @Column(length = 10)
  @ElementCollection(fetch = FetchType.LAZY)
  @Enumerated(EnumType.STRING)
  private List<ChrgSetleMthdCd> availPosChrgSetleMthdCdList  = new ArrayList<>();

  /**
   * 관리자에서 브랜드 관리자에게 보일 메뉴 코드 목록
   */
  @Column(length = 10)
  @ElementCollection(fetch = FetchType.LAZY)
  @Enumerated(EnumType.STRING)
  private List<MenuItemCd> menuItemCdBMstList  = new ArrayList<>();

  /**
   * 관리자에서 상점 관리자에게 보일 메뉴 코드 목록
   */
  @Column(length = 10)
  @ElementCollection(fetch = FetchType.LAZY)
  @Enumerated(EnumType.STRING)
  private List<MenuItemCd> menuItemCdSMstList = new ArrayList<>();

  /**
   * 관리자에서 브랜드 관리자에게 수정 권한을 줄 메뉴 코드 목록
   */
  @Column(length = 10)
  @ElementCollection(fetch = FetchType.LAZY)
  @Enumerated(EnumType.STRING)
  private List<MenuItemCd> editableMenuItemCdBMstList  = new ArrayList<>();

  /**
   * 관리자에서 상점 관리자에게 수정 권한을 줄 메뉴 코드 목록
   */
  @Column(length = 10)
  @ElementCollection(fetch = FetchType.LAZY)
  @Enumerated(EnumType.STRING)
  private List<MenuItemCd> editableMenuItemCdSMstList  = new ArrayList<>();

  /**
   * 브랜드 내 매장에서 사용가능한 전체 서비스 목록
   *
   * 매장마다 서비스 가능한 목록을 설정할 때 사용한다
   */
  @Column(length = 10)
  @Enumerated(EnumType.STRING)
  @ElementCollection(fetch = FetchType.LAZY)
  private List<AvailServiceCd> availServiceCdList  = new ArrayList<>();

  /**
   * 브랜드 내 매장에서 사용가능한 전체 서비스 목록
   *
   * 매장마다 서비스 가능한 목록을 설정할 때 사용한다
   */
  @Column(length = 10)
  @Enumerated(EnumType.STRING)
  @ElementCollection(fetch = FetchType.LAZY)
  private List<DuplicateAvailFieldCd> duplicateAvailFieldCdList  = new ArrayList<>();



//  /**
//   * 자동으로 발급할 선불카드 종류
//   */
//  @ManyToOne
//  @JoinColumn(name = "prpayGoodsSn", insertable = false, updatable = false)
//  private PrpayGoods prpayGoods;

//  /**
//   * 자동으로 발급할 선불카드 종류 일련번호
//   */
//  @Column(name = "prpayGoodsSn")
//  private Integer prpayGoodsSn;

  /**
   *  기본적인 브랜드 옵션 설정
   */
  @OneToOne (cascade = CascadeType.ALL )
  private BrandSetting brandSetting;




  /**
   *  기본적인 브랜드 옵션 설정
   */
  @OneToOne (cascade = CascadeType.ALL )
  private BrandApp brandApp;


  /**
   *  기본적인 안드로이드, IOS 앱  옵션 설정
   */
  @OneToOne (cascade = CascadeType.ALL )
  private BrandAuth brandAuth;


  /**
   * 브랜드 별 환경 변수. 기존 themeValue 대체
   */
//  @ElementCollection
//  @MapKeyColumn(name = "env_key")
//  @Column(name = "env_value")
//  @CollectionTable(name = "brand_env_value_map", joinColumns = @JoinColumn(name = "brand_sn"))
//  private Map<String, String> envValueMap = new HashMap<>();

  @Column(columnDefinition = "json")
  private String envValueMap;

  /**
   * 등록 일시
   */
  @CreationTimestamp
  private ZonedDateTime regDttm;

  /**
   * 수정 일시
   */
  @UpdateTimestamp
  private ZonedDateTime updtDttm;

  @NameDescription("갱신담당자")
  private String updateBy;

  @NameDescription("등록담당자")
  private String createBy;

}
