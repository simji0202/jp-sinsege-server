package kr.co.paywith.pw.common;

import java.time.LocalDateTime;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.StringFixedSaltGenerator;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;



// 테스트를 위한 정보를 외부 설정 파일에 정의
// 자동적으로 application.properties 파일에 설정 할 수 있음

@Component
@ConfigurationProperties(prefix = "sinsege")
@Getter
@Setter
public class AppProperties {

    // 항목별로 설정가능
    // prefix.설정항목
    // my-app.adminUsername

  @NotEmpty
  private String adminId;

  @NotEmpty
  private String adminPw;

  @NotEmpty
  private String userUsername;

  @NotEmpty
  private String userPassword;

  @NotEmpty
  private String clientId;

  @NotEmpty
  private String clientSecret;

  @NameDescription("연락처")
  @NotEmpty
  private String  tel;

  @NameDescription("팩스번호")
  @NotEmpty
  private String  fax;

  @NameDescription("주소")
  @NotEmpty
  private String  addr;

  @NameDescription("법인등록번호")
  @NotEmpty
  private String  coRegNo;

  @NameDescription("여행업등록번호")
  @NotEmpty
  private String  travalAgencyRegNo;

  @NameDescription("대표자")
  @NotEmpty
  private String  revName;

  @NameDescription("로고")
  @NotEmpty
  private String  logoUrl;

  // 연동 아이디
  private String linkID;

  // 인증 키
  private String secretKey;

  // 테스트 여부
  private Boolean isTest;

  private String userID;
  private String senderNum;
  private String corpNum;
  private String plusFriendID;


  private String mpCryptoPassword;
  private String stringOutputType;
  private String stringFixedSaltGenerator;


  public String getPlusFriendID() throws Exception {
    return new String(plusFriendID.getBytes("ISO-8859-1"), "UTF-8");
  }

  // 암호화
  public String encryptionUsing(String value) {

    String mpCryptoPassword = getMpCryptoPassword();

    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    encryptor.setStringOutputType(getStringOutputType());
    encryptor.setPassword(mpCryptoPassword);
    encryptor.setSaltGenerator(new StringFixedSaltGenerator(getStringFixedSaltGenerator()));
    String encryptedPassword = encryptor.encrypt(value);

    return encryptedPassword;

  }

  public String decryptionUsing(String value) {

    String mpCryptoPassword = getMpCryptoPassword();

    StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
    decryptor.setStringOutputType(getStringOutputType());
    decryptor.setPassword(mpCryptoPassword);
    decryptor.setSaltGenerator(new StringFixedSaltGenerator(getStringFixedSaltGenerator()));

    return decryptor.decrypt(value);

  }

}
