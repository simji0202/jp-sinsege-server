package kr.co.paywith.pw.data.repository.mbs.cd.addr3;

import com.opencsv.bean.CsvBindByName;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import kr.co.paywith.pw.data.repository.mbs.cd.addr2.CdAddr2;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 시군구 코드
 */
@Data
@Entity
@Table
@DynamicUpdate
public class CdAddr3 {

  /**
   * 시군구 코드
   */
  @Id
  @Column
  @CsvBindByName(column = "AddrCd3")
  private String cd;

  /**
   * 시군구 명
   */
  @Column
  @CsvBindByName(column = "name")
  private String name;

  /**
   * 시도
   */
  @ManyToOne
  private CdAddr2 cdAddr2;
}
