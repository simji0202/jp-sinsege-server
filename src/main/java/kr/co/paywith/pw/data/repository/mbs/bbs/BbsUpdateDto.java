package kr.co.paywith.pw.data.repository.mbs.bbs;

import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.user.user.UserInfo;
import lombok.Data;

import javax.persistence.Lob;
import java.time.ZonedDateTime;

/**
 * 게시판(게시물)
 */
@Data
public class BbsUpdateDto {

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
     * 게시물 비밀글 여부
     */
    private Boolean secretFl;

    /**
     * 게시물 삭제 표시 여부
     */
    private Boolean delFl = false;

    private UserInfo userInfo;

    private Boolean openedFl;

    private ZonedDateTime startDttm;

    private ZonedDateTime endDttm;

    private Mrhst mrhst;

}
