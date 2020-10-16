package kr.co.paywith.pw.data.repository.admin;


import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.agents.Agents;
import kr.co.paywith.pw.data.repository.partners.Partners;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Admin {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NameDescription("관리자, 업체, 에이전트 id")
  private String adminId;

  @NameDescription("이름")
  private String adminNm;

  @NameDescription("타입(관리자, 여행사, 버스업체 )")
  @Enumerated(EnumType.STRING)
  private AdminType adminType;

  @NameDescription("비밀번호")
  private String adminPw;

  @NameDescription("이메일(아이디)")
  private String  email;

  @NameDescription("관리자타입")
  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  private Set<AdminRole> roles;

  @NameDescription("파트너 업체 ")
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Partners partners;

  @NameDescription("에이젼트 ")
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Agents agents;


  // test 입니다
  // 공통 부분
  @LastModifiedDate
  @NameDescription("갱신일")
  private LocalDateTime updateDate;

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
