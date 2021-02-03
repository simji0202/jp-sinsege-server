package kr.co.paywith.pw.data.repository.prx.certPhone;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CertRequestModel {

  @NotNull
  private String brandCd;

  @NotNull
  private String corpNo;

  @NotNull
  private String mobileNum;

  private String nm;

}
