package kr.co.paywith.pw.data.repository.user.grade;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class GradeUp {


    /**
     * 회원 앱 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 등급
     */
    @OneToOne
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
