package kr.co.paywith.pw.data.repository.partners;

import kr.co.paywith.pw.common.NameDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusGuideDto {


  @NameDescription("가이드번호")
  private Integer id;

  @NameDescription("가이드이름 ")
  private String guideName;

  @NameDescription("가이드연락처")
  private String guideTel;

  @NameDescription("파트너사")
  @OneToOne
  private Partners partners;


}
