package kr.co.paywith.pw.data.repository.user.grade;

import lombok.Data;


@Data
public class GradeUpDto {

    private Integer id;

    /**
     * 등급
     */
    private Grade grade;

    /**
     * 등급 위한 점수 ( <= )
     */
    private Integer goeValue; // 조건 달성 시 다음 등급으로 진입

    /**
     * 등급 위한 점수 ( < )
     */
    private Integer ltValue; // 다음 등급 갈 때 점수 초기화 여부


}
