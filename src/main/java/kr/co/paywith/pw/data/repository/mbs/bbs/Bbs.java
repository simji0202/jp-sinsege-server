package kr.co.paywith.pw.data.repository.mbs.bbs;

import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.file.File;
import kr.co.paywith.pw.data.repository.mbs.enumeration.BbsTypeCd;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

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
    private Integer parentBbsSn;
    /**
     * 게시물 제목
     */
    @Column(length = 300)
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


    @ManyToOne
    private Admin admin;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private UserInfo userInfo;

    @Column(nullable = true)
    private Integer userSn;

    @OneToMany(fetch = FetchType.LAZY)
    private List<File> fileList;

    private Boolean openedFl;

    private ZonedDateTime startDttm;

    private ZonedDateTime endDttm;

    @ManyToOne
    private Mrhst mrhst;

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
