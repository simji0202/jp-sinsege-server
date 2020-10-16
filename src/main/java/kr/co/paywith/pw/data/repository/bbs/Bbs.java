package kr.co.paywith.pw.data.repository.bbs;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// JPA에서 @Data를 사용하지 않는 이유는
// 모든 항목에 EqualsAndHashCode를 선언 할 경우 연관 관계에 항목일 경우
// equalsandhashcode stackoverflow 가 발생 할 수 있음
// by Che

/**
 * 게시판 정보의 엔티티
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Bbs {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("Id")
  private Integer id;

  @NameDescription("타입")
  @Enumerated(EnumType.STRING)
  private Board type;

  @NameDescription("제목")
  private String title;

  @NameDescription("부제목")
  private String subTitle;

  @NameDescription("내용")
  @Lob
  private String content;

  @NameDescription("조회수")
  private int viewCount;

  @NameDescription("상품이미지")
  private String imageUrl;

  @NameDescription("이벤트 기간  예) 20191102-20200113")
  private String eventTerm;

  @NameDescription("이벤트 진행여부 ")
  @ColumnDefault("0") //default 0
  private boolean isProgress;

  @NameDescription("게시물 삭제여부 ")
  @ColumnDefault("0") //default 0
  private boolean isDelete;

  // 고객게시물내용
  @NameDescription("휴대폰")
  private String cellPhone;

  @NameDescription("이메일")
  private String email;

  @NameDescription("업로드 파일 ")
  @OneToMany (cascade =  {CascadeType.ALL})
  private List<Files> files = new ArrayList<>();

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



