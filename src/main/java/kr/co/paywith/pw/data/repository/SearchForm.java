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

}
