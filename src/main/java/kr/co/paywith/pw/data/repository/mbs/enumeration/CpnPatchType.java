package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

@Getter
public enum CpnPatchType {
  USE("Use");

  private String title;

  CpnPatchType(String title) {
    this.title = title;
  }
}
