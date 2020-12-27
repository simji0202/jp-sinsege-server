package kr.co.paywith.pw.data.repository.mbs.bbs;

import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.file.File;
import kr.co.paywith.pw.data.repository.enumeration.BbsTypeCd;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * 게시판(게시물)
 */
@Data
public class BbsDto {

    /**
     * 게시물 일련번호
     */
    private Integer id;

    /**
     * 게시물 구분코드
     */
    @Enumerated(EnumType.STRING)
    private BbsTypeCd bbsTypeCd;

//    /**
//     * 부모 게시물 일련번호
//     */
//    private Integer parentBbsSn;
    /**
     * 게시물 제목
     */
    private String bbsSj;

    /**
     * 게시물 본문
     */
    @Lob
    private String bbsCn;

    /**
     * 배너 등에 사용할 이미지 웹 경로
     */
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


    private Admin admin;

    private UserInfo userInfo;

    private List<File> fileList;

    private Boolean openedFl;

    private ZonedDateTime startDttm;

    private ZonedDateTime endDttm;

    private Mrhst mrhst;

}
