package kr.co.paywith.pw.data.repository.mbs.globals;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 공통변수 키 관리
 */
@Data
@ToString
@Entity
@Table(name = "PW_GLOBALS")
public class Globals {

  /**
   * 공통 변수 키
   */
  @Id
  @Column(length = 20)
  @Enumerated(EnumType.STRING)
  private GlobalsKey globalsKey;

  /**
   * 공통 변수 값
   */
  private Integer globalsValue;

  public Globals() {
  }

  public Globals(GlobalsKey key) {
    this.globalsKey = key;
    this.globalsValue = 0;
  }
}
