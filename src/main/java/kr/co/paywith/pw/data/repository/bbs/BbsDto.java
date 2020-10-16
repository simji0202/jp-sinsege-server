package kr.co.paywith.pw.data.repository.bbs;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BbsDto {


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
  private String content;

  @NameDescription("조회수")
  private int viewCount;

  @NameDescription("게시물 삭제여부 ")
  @ColumnDefault("0") //default 0
  private boolean isDelete;

  @NameDescription("업로드 파일 ")
  private List<Files> files = new ArrayList<>();

}



