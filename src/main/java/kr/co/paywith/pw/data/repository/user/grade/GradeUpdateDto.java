package kr.co.paywith.pw.data.repository.user.grade;

import kr.co.paywith.pw.data.repository.enumeration.GradeUpRuleCd;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Data
public class GradeUpdateDto {

    private Integer id;

    /**
     * 등급 명
     */
    private String gradeNm;

    /**
     * 등급 표시 코드
     */
    private String gradeCode;

}
