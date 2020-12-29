package kr.co.paywith.pw.data.repository.user.grade;

import kr.co.paywith.pw.data.repository.enumeration.GradeUpRuleCd;
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
public class Grade {

//
// 1    "VIP"     유효기간   조건       5       5000  4000  <<  포인트 ,  스템프,   사용한 누적금액,   스코아
// 5    TEST                        4
//            2    "GOLD"    유효기간   조건      3      3999  2000
//            3    "SILVER"  유효기간   조건      2
//            4    "일반"                      1
//
//
//
//    브랜드  인스던스   DB


    /**
     * 회원 앱 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 등급 명
     */
    private String gradeNm;

    /**
     * 등급 업 규칙(달성하면 다음 등급으로 상승)
     */
    @Column(nullable = true, length = 10)
    @Enumerated(EnumType.STRING)
    private GradeUpRuleCd gradeUpRuleCd;

    /**
     * 등급 업 규칙 달성을 위한 점수
     */
    @Column(nullable = true)
    private Integer gradeUpValue; // 조건 달성 시 다음 등급으로 진입

    /**
     * 다음 등급 갈 때 점수 초기화 여부
     */
    private Boolean initScoreFl; // 다음 등급 갈 때 점수 초기화 여부

    /**
     * 다음 등급 진입 시 gradeUpValue 만큼 점수 차감 여부(GradeUpRuleCd.CS 일 때)
     */
    private Boolean minusScoreFl;

    /**
     * 등급 유지 기간 일수(기간 이후 이전 등급으로 복귀)
     */
    private Integer validDay;


    // 20201216 신규 추가 ( 브랜드 정보 참조 )
//    /**
//     * 브랜드
//     */
//    @ManyToOne(cascade = CascadeType.ALL)
//    private Brand brand;

    /**
     * 등급 순서. 0 -> 1 -> 2 순서대로 등급이 올라감. 0 은 회원가입 직후 등급
     */
    private Integer sort;

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


}
