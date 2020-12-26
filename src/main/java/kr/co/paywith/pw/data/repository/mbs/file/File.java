package kr.co.paywith.pw.data.repository.mbs.file;

import kr.co.paywith.pw.common.NameDescription;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
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
@DynamicUpdate
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NameDescription("식별번호")
    private Integer id;

    /**
     * 첨부파일 순서
     */
    private Integer ordr;
    /**
     * 첨부파일 원본 파일명
     */
    private String originalNm;
    /**
     * 첨부파일 웹 경로
     */
    private String fileUrl;

    /**
     * 게시판 일련번호
     */
//    @Column(table = "PW_FILE_BBS")
    private Integer bbsSn;

    /**
     * 등록 일시
     */
    @CreationTimestamp
    private ZonedDateTime regDttm;
}