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
     * 등급 표시 코드
     */
    private String gradeCode;


}
