package kr.co.paywith.pw.data.repository.mbs.bbs;

import kr.co.paywith.pw.data.repository.enumeration.BbsTypeCd;
import kr.co.paywith.pw.data.repository.file.File;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.user.userInfo.UserInfo;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * 게시판(게시물)
 */
@Data
public class BbsDto {

    /**
     * 게시물 구분코드
     */
    private BbsTypeCd bbsTypeCd;

    /**
     * 부모 게시물 일련번호
     */
    private Integer parentId;

    /**
     * 게시물 제목
     */
    private String bbsSj;

    /**
     * 게시물 본문
     */
    private String bbsCn;

    /**
     * 배너 등에 사용할 이미지 웹 경로
     */
    private String imgUrl;

    /**
     * 게시물 비밀글 여부
     */
    private Boolean secretFl;

    private UserInfo userInfo;

    private Boolean openedFl;

    private ZonedDateTime startDttm;

    private ZonedDateTime endDttm;

    private Mrhst mrhst;

}

