package kr.co.paywith.pw.data.repository.mbs.cd.addr;

import com.opencsv.bean.CsvBindByName;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


/**
 * 시도코드
 */
@Data
@Entity
@Table(name = "CD_ADDR1")
@DynamicUpdate
public class CdAddr1 {

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
  private String name;

}
