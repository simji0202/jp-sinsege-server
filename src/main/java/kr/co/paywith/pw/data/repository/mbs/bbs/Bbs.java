package kr.co.paywith.pw.data.repository.mbs.bbs;

import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.enumeration.BbsTypeCd;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * 게시판(게시물)
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Bbs {

    /**
     * 게시물 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    /**
     * 게시물 구분코드
     */
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private BbsTypeCd bbsTypeCd;

    /**
     * 부모 게시물 일련번호
     */
    @Column(nullable = true)
    private Integer parentId;
    /**
     * 게시물 제목
     */
    @Column
    private String bbsSj;

    /**
     * 게시물 본문
     */
    @Column
    @Lob
    private String bbsCn;

    /**
     * 배너 등에 사용할 이미지 웹 경로
     */
    @Column
    private String imgUrl;

    /**
     * 게시물 조회수
     */
    private Integer viewCnt = 0;
    /**
     * 게시물 비밀글 여부
     */
    private Boolean secretFl;

    /**
     * 게시물 삭제 표시 여부
     */
    private Boolean delFl = false;

    /**
     * 조회가능한 회원. QnA 등에서 관리자와 회원, 매장과 회원 간 글 작성에 활용
     */
    @ManyToOne
    private UserInfo userInfo;

//     TODO pw-file 업로드 후 URL과 이름만 기록
//    @OneToMany(fetch = FetchType.LAZY)
//    private List<File> fileList;

    private Boolean openedFl;

    /**
     * 이벤트 등 시작 시각
     */
    private ZonedDateTime startDttm;

    /**
     * 이벤트 등 종료 시각
     */
    private ZonedDateTime endDttm;

    /**
     * 조회가능한 매장. QnA 등에서 관리자와 회원, 매장과 회원 간 글 작성에 활용. 매장 공지에 활용
     */
    @ManyToOne
    private Mrhst mrhst;


    /// 공통 항목 ////
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

    @NameDescription("갱신담당자")
    private String updateBy;

    @NameDescription("등록담당자")
    private String createBy;

    @NameDescription("삭제담당자")
    private String deleteBy;

}
