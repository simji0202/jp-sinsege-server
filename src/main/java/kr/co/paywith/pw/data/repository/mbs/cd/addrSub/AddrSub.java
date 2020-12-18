package kr.co.paywith.pw.data.repository.mbs.cd.addrSub;

import com.opencsv.bean.CsvBindByName;
import kr.co.paywith.pw.data.repository.mbs.cd.addr.Addr;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * 시군구 코드
 */
@Data
@ToString(exclude = {"mrhstList"})
@Entity
@Table(name = "CD_ADDR_SUB")
@DynamicUpdate
public class AddrSub {

  /**
   * 시군구 코드
   */
  @Id
  @Column(length = 10, name = "cd")
  @CsvBindByName(column = "AddrCd2")
  private String cd;
  /**
   * 시군구 명
   */
  @Column(length = 50, name = "name")
  @CsvBindByName(column = "name")
  private String addrSubName;
  /**
   * 시도 코드
   */
  @Column
  @CsvBindByName(column = "AddrCd1")
  private String addrCd;
  /**
   * 시도
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "addrCd", insertable = false, updatable = false)
  private Addr addr;

  /**
   * 속한 매장 목록
   */
  @OneToMany(mappedBy = "addrSub", fetch = FetchType.LAZY)
  private List<Mrhst> mrhstList;
}
