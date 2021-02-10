package kr.co.paywith.pw.data.repository.mbs.stampHist;

import java.time.LocalDateTime;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import kr.co.paywith.pw.data.repository.enumeration.StampHistType;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StampHistDto {

  /**
   * 회원
   */
  private UserInfo userInfo;

  /**
   * 스탬프 이력 구분 코드
   */
  @Enumerated(EnumType.STRING)
  private StampHistType stampHistType;

  /**
   * 처리 일시
   */
  private LocalDateTime setleDttm = LocalDateTime.now();

  /**
   * 스탬프 개수
   */
  private Integer cnt = 0;

  /**
   * 요청 단말기 번호
   */
  private String trmnlNo;

  /**
   * 단말기 영수증 번호
   */
  private String trmnlDelngNo;

  /**
   * 가맹점
   */
  private Mrhst mrhst;
}
