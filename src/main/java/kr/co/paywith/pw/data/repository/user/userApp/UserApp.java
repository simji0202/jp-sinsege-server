package kr.co.paywith.pw.data.repository.user.userApp;

import kr.co.paywith.pw.data.repository.enumeration.UserAppOsCd;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserApp {

    /**
     * 회원 앱 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 등록 일시
     */
    @CreationTimestamp
    private ZonedDateTime regDttm;

    /**
     * 수정 일시
     */
    @UpdateTimestamp
    private ZonedDateTime updtDttm;

    /**
     * 회원 앱 구분 코드
     */
    @Enumerated(EnumType.STRING)
    private UserAppOsCd userAppOsCd;
    /**
     * 단말기 아이디(식별 고유 키)
     */
    private String trmnlId;

    /**
     * 푸시 메시지 수신 여부
     */
    private Boolean pushFl;
    /**
     * 단말기 푸시 FCM 키
     */
    private String pushKey;

    /**
     * 앱 버전 명
     */
    private String appVerNm;

    /**
     * 활성여부
     */
    private Boolean activeFl = true;

}
