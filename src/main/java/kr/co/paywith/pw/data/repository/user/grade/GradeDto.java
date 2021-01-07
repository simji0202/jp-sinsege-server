package kr.co.paywith.pw.data.repository.user.grade;

import lombok.Data;


@Data
public class GradeDto {

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
