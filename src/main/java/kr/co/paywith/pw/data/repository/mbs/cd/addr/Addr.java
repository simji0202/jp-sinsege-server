package kr.co.paywith.pw.data.repository.mbs.cd.addr;

import com.opencsv.bean.CsvBindByName;

import kr.co.paywith.pw.data.repository.mbs.cd.addrSub.AddrSub;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;


/**
 * 시도코드
 */
@Data
@ToString(exclude = {"addrSubList", "mrhstList"})
@Entity
@Table(name = "CD_ADDR")
@DynamicUpdate
public class Addr {

  /**
   * 시도 코드
   */
  @Id
  @Column(length = 10, name = "cd")
  @CsvBindByName(column = "AddrCd1")
  private String cd;
  /**
   * 시도 명
   */
  @Column(length = 50, name = "name")
  @CsvBindByName(column = "Name")
  private String addrName;

  @OneToMany(mappedBy = "addr", fetch = FetchType.LAZY)
  @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
  private List<AddrSub> addrSubList;


  /**
   * 속한 매장 목록
   */
  @OneToMany(mappedBy = "addr", fetch = FetchType.LAZY)
  private List<Mrhst> mrhstList;

}
