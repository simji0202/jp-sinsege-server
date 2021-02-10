package kr.co.paywith.pw.data.repository.file;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 첨부파일
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class File implements Serializable {


  /**
   * 첨부파일 일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /**
   * 첨부파일 순서
   */
  // kms: sort로 변경. ordr은 pw-ordr 과 단어 중복. sort로
  private Integer sort;
  /**
   * 첨부파일 원본 파일명
   */
  private String originalNm;
  /**
   * 첨부파일 웹 경로
   */
  private String fileUrl;

  // kms:
//  /**
//   * 게시판 일련번호
//   */
//  private Integer bbsId;

  /**
   * 등록 일시
   */
  @CreationTimestamp
  private LocalDateTime regDttm;
}
