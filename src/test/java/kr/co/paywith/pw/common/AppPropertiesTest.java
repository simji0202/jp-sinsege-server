package kr.co.paywith.pw.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


// 테스트를 위한 정보를 외부 설정 파일에 정의
// 자동적으로 application.properties 파일에 설정 할 수 있음

@Component
@ConfigurationProperties(prefix = "sinsege")
@Getter
@Setter
public class AppPropertiesTest {

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


}
