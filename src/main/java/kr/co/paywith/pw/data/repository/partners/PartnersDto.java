package kr.co.paywith.pw.data.repository.partners;

import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnersDto {


  @NameDescription("업체번호")
  private Integer id;

  @NameDescription("업체아이디 ")
  private String adminId;


  @NameDescription("password ")
  private String password;

  /////// 담당자 이름 ////////////
  @NameDescription("이름  ")
  private String name;

  @NameDescription("firstName")
  private String  firstName;

  @NameDescription("lastName")
  private String  lastName;

  @NameDescription("이메일(아이디)")
  private String  email;

  @NameDescription("이메일 알림 수신여부")
  private boolean  isEmailReceving;

  ////////  휴대폰  ////////
  @NameDescription("휴대폰 연락처 국가코드")
  private String  phoneCrCd;

  @NameDescription("전화번호")
  private String phone;

  //////// sms수신여부  /////
  @NameDescription("sms수신여부")
  private boolean  isSmsReceving;

  //////// 비상 연락처  /////
  @NameDescription("비상연락처 국가코드")
  private String  emcTelCrCd;

  @NameDescription("비상연락처")
  private String  emcTel;

  /////// 소셜 아이디 ///////
  @NameDescription("카카오톡 아이디")
  private String  kakaoId;

  @NameDescription("위쳇 아이디")
  private String  wechatId;

  @NameDescription("라인 아이디")
  private String  lineId;

  @NameDescription("명함 사진 url")
  private String callingCardImgUrl;

  @NameDescription("메인 이미지 url")
  private String logoImgUrl;

  @NameDescription("상태")
  @Enumerated(EnumType.STRING)
  private PartnerStatus status;

  @NameDescription("최근 로그인 일자")
  private LocalDateTime  lastLoginDate;

  @NameDescription("회사정보 ")
  @OneToOne
  private Company company;


}
