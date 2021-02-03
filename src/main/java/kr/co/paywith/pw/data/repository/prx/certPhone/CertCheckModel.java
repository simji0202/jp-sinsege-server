package kr.co.paywith.pw.data.repository.prx.certPhone;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CertCheckModel {

  @NotNull
  private String brandCd;

  @NotNull
  private String corpNo;

  @NotNull
  private String mobileNum;

  @NotNull
  private Integer certPhoneId;

  private String certNum;

  private Boolean extendFl = false;

}
