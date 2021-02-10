package kr.co.paywith.pw.data.repository.code.addrCode;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.mbs.mrhstDelivAmt.MrhstDelivAmt;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 주소 코드
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class AddrCode {

  /**
   * 코드
   */
  @Id
  private String cd;

  /**
   * 코드 명
   */
  private String nm;

  /**
   * 이 코드를 사용하는 매장
   */
  @JsonIgnore
  @OneToMany(mappedBy = "addrCode")
  private List<Mrhst> mrhstList;

  /**
   * 이 코드를 사용하는 배달비. 코드 삭제시 전체 삭제함
   */
  @JsonIgnore
  @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "addrCode")
  private List<MrhstDelivAmt> mrhstDelivAmtList;

  @NameDescription("갱신 일시")
  private LocalDateTime updtDttm;

  @NameDescription("등록 일시")
  private LocalDateTime regDttm;
}
