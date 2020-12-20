package kr.co.paywith.pw.data.repository.user.grade;

import kr.co.paywith.pw.data.repository.enumeration.GradeUpRuleCd;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Data
public class GradeUpdateDto {



    /**
     * 회원 앱 일련번호
     */
    private Integer id;

    /**
     * 등급 명
     */
    private String gradeNm;

    /**
     * 등급 업 규칙(달성하면 다음 등급으로 상승)
     */
    @Enumerated(EnumType.STRING)
    private GradeUpRuleCd gradeUpRuleCd;

    /**
     * 등급 업 규칙 달성을 위한 점수
     */
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


}
