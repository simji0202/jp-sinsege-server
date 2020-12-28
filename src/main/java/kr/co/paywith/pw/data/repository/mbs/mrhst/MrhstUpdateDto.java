package kr.co.paywith.pw.data.repository.mbs.mrhst;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vividsolutions.jts.geom.Point;
import kr.co.paywith.pw.data.repository.enumeration.AvailServiceCd;
import kr.co.paywith.pw.data.repository.mbs.cd.addr.Addr;
import kr.co.paywith.pw.data.repository.mbs.cd.addrSub.AddrSub;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
public class MrhstUpdateDto {

    private Integer id;

    /**
     * 가맹점 이름
     */
    private String mrhstNm;

    /**
     * 가맹점 설명
     */
    private String mrhstCn;

    /**
     * 전화번호
     */
    private String tel;
    /**
     * 주소
     */
    private String address;
    /**
     * 시도 코드
     */

    private String addrCd;
    /**
     * 시군구 코드
     */

    private String addrSubCd;
    /**
     * 운영 시간
     */
    private String openTm;
    /**
     * 위도
     */
    private Double lat;
    /**
     * 경도
     */
    private Double lng;
    /**
     * 가맹점 사업자 번호
     */
    private String corpNo;
    /**
     * 가맹점 대표자 성명
     */
    private String ceoName;

    /**
     * 운영 여부. 앱에서 기본적으로 표시할 지 안할 지를 설정.
     */
    private Boolean openFl;
    /**
     * 선불카드 사용 여부
     */
    private Boolean usePrpayFl;

    /**
     * 가맹점 서비스 가능 목록
     */
    @Enumerated(EnumType.STRING)
    private List<AvailServiceCd> availServiceCdList;

    /**
     * 기타 내용
     */
    private String addData;


    /**
     * 가맹점 코드 (POS 연동)
     */
    private String mrhstCd;

    /**
     * 현금영수증 연동 아이디(팝빌)
     * <p>
     * 아이디가 없고 corpNo와 ceoName이 DB 에 있다면 팝빌 서비스와 연동하여 아이디 발급
     */
    private String pobbillId;

    @JsonIgnore
    private Point coords;

    /**
     * 가맹점 소개 대표 이미지
     */
    @Column
    private String imgUrl;

    /**
     * 가맹점 소개 대표 이미지
     */
    @Column
    private List<String> imgUrlList;

    @Transient
    private Double distance;

    /**
     * 시도코드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addrCd", insertable = false, updatable = false)
    private Addr addr;

    /**
     * 시군구코드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addrSubCd", insertable = false, updatable = false)
    private AddrSub addrSub;

}
