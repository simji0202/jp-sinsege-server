package kr.co.paywith.pw.data.repository.file;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

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
   *
   */
  private static final long serialVersionUID = -7183100274674419118L;
  /**
   * 첨부파일 일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /**
   * 첨부파일 순서
   */
  private Integer ordr;
  /**
   * 첨부파일 원본 파일명
   */
  private String originalNm;
  /**
   * 첨부파일 웹 경로
   */
  private String fileUrl;

  /**
   * 게시판 일련번호
   */
  private Integer bbsSn;

  /**
   * 등록 일시
   */
  @CreationTimestamp
  private ZonedDateTime regDttm;
}
