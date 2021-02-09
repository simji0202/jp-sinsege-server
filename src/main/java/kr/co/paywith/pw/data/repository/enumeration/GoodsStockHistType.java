package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum GoodsStockHistType {
  IN("반입"),
  OUT("반출"),
  ;

  private String title;

  GoodsStockHistType(String title) {
    this.title = title;
  }
}
