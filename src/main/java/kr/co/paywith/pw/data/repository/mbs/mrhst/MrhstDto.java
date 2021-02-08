package kr.co.paywith.pw.data.repository.mbs.mrhst;

import java.util.List;
import kr.co.paywith.pw.data.repository.enumeration.AvailServiceType;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.cd.addr.CdAddr1;
import kr.co.paywith.pw.data.repository.mbs.cd.addr2.CdAddr2;
import kr.co.paywith.pw.data.repository.mbs.cd.addr3.CdAddr3;
import kr.co.paywith.pw.data.repository.mbs.mrhstOrdr.MrhstOrdr;
import kr.co.paywith.pw.data.repository.mbs.mrhstOrdr.MrhstOrdrDto;
import lombok.Data;

@Data
public class MrhstDto {

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
  private CdAddr1 cdAddr1;

  private CdAddr2 cdAddr2;

  private CdAddr3 cdAddr3;

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
   * 가맹점 서비스 가능 목록
   */
  private List<AvailServiceType> availServiceTypeList;

  /**
   * 기타 내용
   */
  private String envValueMap;

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
   * 가맹점 소개 대표 이미지
   */
  private List<String> imgUrlList;

  /**
   * 매장 주문 설정 정보
   */
  private MrhstOrdrDto mrhstOrdr;

  private Brand brand;

}
