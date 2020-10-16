package kr.co.paywith.pw.data.repository.partners;

import kr.co.paywith.pw.common.NameDescription;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 여행사 ( 기본정보 + 로그인 정보(Admin)  )
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("업체번호")
  private Integer id;

  ////// 회사 정보 ////
  @NameDescription("회사명")
  private String  companyNm;

  @NameDescription("회사코드")
  private String code;

  /////// 대표자 ////
  @NameDescription("대표자 firstName")
  private String  repFirstName;

  @NameDescription("대표자 lastName")
  private String  repLastName;

  @NameDescription("대표자 firstName Kana")
  private String  repFirstNameKana;

  @NameDescription("대표자 lastName Kana")
  private String  repLastNameKana;

  @NameDescription("대표자 전화 국가코드")
  private String  repPhoneCrCd;

  @NameDescription("대표자 연락처")
  private String  repPhone;

  @NameDescription("대표자 팩스 국가코드")
  private String  repFaxCrCd;

  @NameDescription("대표자 팩스 번호")
  private String  repFax;

  @NameDescription("주소")
  private String address;

  @NameDescription("담당자이름")
  private String manager;

  @NameDescription("메인 이미지 url")
  private String logoImgUrl;

  @NameDescription("통장사본")
  private String  bankBookUrl;


  @NameDescription("사업자번호")
  private String businessLicenseNumber;

  @NameDescription("사업자등록증 사진 url")
  private String businessLicenseImgUrl;




}
