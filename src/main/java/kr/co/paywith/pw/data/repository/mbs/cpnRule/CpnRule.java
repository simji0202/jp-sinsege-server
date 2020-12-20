package kr.co.paywith.pw.data.repository.mbs.cpnRule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;
import com.vividsolutions.jts.geom.Point;
import kr.co.paywith.pw.data.repository.mbs.cd.addr.Addr;
import kr.co.paywith.pw.data.repository.mbs.cd.addrSub.AddrSub;
import kr.co.paywith.pw.data.repository.mbs.cpnMaster.CpnMaster;
import kr.co.paywith.pw.data.repository.mbs.enumeration.AvailServiceCd;
import kr.co.paywith.pw.data.repository.mbs.enumeration.CpnIssuRuleCd;
import kr.co.paywith.pw.data.repository.user.grade.Grade;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * 가맹점
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity

@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class CpnRule {

    /**
     * 쿠폰 발급 규칙 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * 발급 대상 회원 등급
     */
    @ManyToOne
    private Grade grade;
    /**
     * 발급 대상 회원 등급 일련번호
     */
    /**
     * 쿠폰 발급 규칙 코드
     */
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private CpnIssuRuleCd cpnIssuRuleCd;
    /**
     * 쿠폰 발급 규칙 명
     */
    private String ruleNm;
    /**
     * 쿠폰 발급 규칙 기준 값
     *
     * cpnIssuRuleCd에 따라 다르게 사용
     *
     * AC, AU, C, U : 기준 금액
     *
     * J, BD, S, GU : 대상회원에게 발급할 시간(0~23)
     */
    private Integer ruleValue;


//    @ManyToOne
//    private QuestCpnRule questCpnRule;

    /**
     * 쿠폰 발급 시 메시지 전송 여부
     */
    private Boolean msgSendFl = false;

    /**
     * 이메일, 푸시 등 발급시 전송할 메시지 본문
     */
    @Column(length = 4000)
    private String msg;

    /**
     * 쿠폰 발급 전송 메시지 제목
     */
    @Column(length = 100)
    private String msgSj;

    /**
     * 발급할 쿠폰 종류
     */
    @ManyToOne
    private CpnMaster cpnMaster;

    /**
     * 발급 후 회원 노출까지 지연시킬 시간(부하 대비 미리 발급)
     */
    private Integer delayHr = 0;

    /**
     * 발급 후 메시지 전송 지연 시간
     */
    private Integer msgDelayHr = 0;

    /**
     * 활성 여부
     */
    private Boolean activeFl;

    /**
     * 발급 규칙 매칭 월
     */
    private Integer ruleMonth;
    /**
     * 발급 규칙 매칭 일
     */
    private Integer ruleDay;
    /**
     * 발급 규칙 매칭 주
     */
    private Integer ruleWeekOfMonth;
    /**
     * 발급 규칙 매칭 요일
     */
    private Integer ruleDayOfWeek;
    /**
     * 발급 규칙 매칭 시간
     */
    private Integer ruleHour;

    /**
     * 발급 규칙 매칭 분
     */
    private Integer ruleMinute;

    /**
     * 생일 이전 발급 일
     */
    private Integer dayBeforeBrth;

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
     * 추가한 관리자
     * 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
     */
    private String createBy;

    /**
     * 변경한  관리자
     * 부하를 줄이기 위해 감소 시키지 위해 해당 아이디만 저장
     */
    private String updateBy;


}
