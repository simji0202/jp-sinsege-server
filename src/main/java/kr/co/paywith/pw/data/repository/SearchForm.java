package kr.co.paywith.pw.data.repository;


import java.util.List;
import javax.persistence.*;

import kr.co.paywith.pw.common.NameDescription;


import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;


@Data
@ToString
public class SearchForm {

  // 공통
  @NameDescription("Id")
  private Integer id;


  @NameDescription("Id")
  private String adminId;


  @NameDescription("사용처리 Id")
  private Long useId;

  @NameDescription("삭제/취소 레코드 포함 여부. 삭제만(Y), 삭제제외(N), 전체(null)")
  private String delYn;

  // 게시판

  private String bbsSj;


}
