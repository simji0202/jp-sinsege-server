package kr.co.paywith.pw.data.repository.bbs;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Files {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NameDescription("Id")
  private Integer id;

  @NameDescription("파일이름")
  private String fileName;

  @NameDescription("경로")
  private String path;

}
