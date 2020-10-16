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
import java.util.HashSet;
import java.util.Set;

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
public class Partners {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("업체번호")
  private Integer id;

  @NameDescription("업체아이디 ")
  private String adminId;

  /////// 담당자 이름 ////////////
  @NameDescription("관리자 이름")
  private String name;

  @NameDescription(" 성 ")
  private String  firstName;

  @NameDescription(" 이름  ")
  private String  lastName;

  @NameDescription("성 (가나)")
  private String  firstNameKana;

  @NameDescription("이름 (가나)")
  private String  lastNameKana;

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

  // 공통 부분
  @LastModifiedDate
  @NameDescription("갱신일")
  private LocalDateTime updateDate;

  @LastModifiedBy
  @NameDescription("갱신담당자")
  private String updateBy;

  @CreatedDate
  @NameDescription("등록일")
  private LocalDateTime createDate;

  @NameDescription("등록담당자")
  private String createBy;

  @NameDescription("변경내용")
  private String updateContent;

}
