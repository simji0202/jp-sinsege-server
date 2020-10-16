package kr.co.paywith.pw.data.repository.partners;


import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnersListDto {

  private Integer id;
  private String companyNm;
  private String adminId;
  private String name;
  private String email;
  private String phone;
  private String code;

  @NameDescription("가입일")
  private LocalDateTime createDate;

  @NameDescription("최근 로그인 일 ")
  private LocalDateTime lastLoginDate;

  @Enumerated(EnumType.STRING)
  private PartnerStatus status;

}
