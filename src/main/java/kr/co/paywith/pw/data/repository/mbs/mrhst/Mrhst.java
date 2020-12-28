package kr.co.paywith.pw.data.repository.mbs.mrhst;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;
import com.vividsolutions.jts.geom.Point;
import kr.co.paywith.pw.data.repository.mbs.cd.addr.Addr;
import kr.co.paywith.pw.data.repository.mbs.cd.addrSub.AddrSub;
import kr.co.paywith.pw.data.repository.enumeration.AvailServiceCd;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * 가맹점
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Mrhst {

    /**
     * 가맹점 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByName(column = "id")
    private Integer id;

    /**
     * 가맹점 이름
     */
    @CsvBindByName(column = "Name", required = true)
    private String mrhstNm;

    /**
     * 가맹점 설명
     */
    @CsvBindByName(column = "mrhstCn", required = true)
    @Lob
    private String mrhstCn;

    /**
     * 전화번호
     */
    @CsvBindByName(column = "Tel")
    private String tel;
    /**
     * 주소
     */
    @CsvBindByName(column = "Address")
    private String address;
    /**
     * 시도 코드
     */
    @CsvBindByName(column = "AddressCd1")
    @Column(length = 10)
    private String addrCd;
    /**
     * 시군구 코드
     */
    @CsvBindByName(column = "AddressCd2")
    @Column(length = 10)
    private String addrSubCd;
    /**
     * 운영 시간
     */
    @CsvBindByName(column = "OpenTime")
    private String openTm;
    /**
     * 위도
     */
    @CsvBindByName(column = "Latitude")
    @Column(columnDefinition = "Decimal(10,6)")
    private Double lat;
    /**
     * 경도
     */
    @CsvBindByName(column = "Longitude")
    @Column(columnDefinition = "Decimal(10,6)")
    private Double lng;
    /**
     * 가맹점 사업자 번호
     */
    @CsvBindByName(column = "CorporationNumber")
    @Column(length = 100)
    private String corpNo;
    /**
     * 가맹점 대표자 성명
     */
    @CsvBindByName(column = "CEOName")
    @Column(length = 100)
    private String ceoName;


    /**
     * 운영 여부. 앱에서 기본적으로 표시할 지 안할 지를 설정.
     */
    @CsvBindByName(column = "Open", required = true)
    private Boolean openFl;

    // kms: 삭제가능. availServiceCdList.PRPAY 로 대체
//    /**
//     * 선불카드 사용 여부
//     */
//    @CsvBindByName(column = "UsePrpay", required = true)
//    private Boolean usePrpayFl;

    /**
     * 가맹점 서비스 가능 목록
     */
    @Column
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<AvailServiceCd> availServiceCdList;

    /**
     * 기타 내용
     */
    @CsvBindByName(column = "Data")
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

    /**
     * 위치정보에 대한 hex 값으로, DB에만 저장하고 json 값으로는 전달하지 않는다.
     */
    @Column(columnDefinition = "geometry")
    @JsonIgnore
    private Point coords;

    // kms: 삭제가능. imgUrlList 로 대체
//    /**
//     * 가맹점 소개 대표 이미지
//     */
//    @Column
//    private String imgUrl;

    /**
     * 가맹점 소개 대표 이미지
     */
    @Column
    @ElementCollection
    private List<String> imgUrlList;

    @Transient
    private Double distance;

    // kms: 국외 지역 코드 조사 및 체계 적용 필요
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
