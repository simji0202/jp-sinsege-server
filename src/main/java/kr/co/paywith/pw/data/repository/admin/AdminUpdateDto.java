package kr.co.paywith.pw.data.repository.admin;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.enumeration.AuthCd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AdminUpdateDto {


  private Integer id;

  @NameDescription("관리자")
  private String adminId;

  @NameDescription("이름")
  private String adminNm;

  @NameDescription("비밀번호")
  private String adminPw;


  @NameDescription("이메일 주소")
  private String emailAddr;

  @NameDescription("휴대폰 번호")
  private String mobileNum;

  @NameDescription("최종 로그인 아이피")
  private String lastLoginIp;

  @NameDescription("권한 코드")
  @Enumerated(EnumType.STRING)
  private AuthCd authCd;

  @NameDescription("브랜드정보")
  private Brand brand;

  @NameDescription("사용 여부")
  private Boolean activeFl;

  @NameDescription("최종 로그인 일시")
  private ZonedDateTime lastLoginDttm;

  @NameDescription("관리자타입")
  @Enumerated(EnumType.STRING)
  private Set<AdminRole> roles;

}
