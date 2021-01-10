package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * The ReserveOrdrType enumeration.
 */
@Getter
public enum ReserveBusinessType implements EnumMapperType {
  FOOD("레스토랑"), BEAUTY("미용실");

  private String title;

  ReserveBusinessType(String title) {
    this.title = title;
  }

  @Override
  public String getCode() {
    return name();
  }
}
