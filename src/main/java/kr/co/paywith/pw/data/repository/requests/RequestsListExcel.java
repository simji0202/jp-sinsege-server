package kr.co.paywith.pw.data.repository.requests;

import kr.co.paywith.pw.common.NameDescription;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
public class RequestsListExcel {

  private String id;


  @NameDescription("예약번호")
  private String  reqNo;

  @NameDescription("예약상태코드(확정대기,예약확정,출발완료,예약취소)")
  private String  reqStateCd;

  @NameDescription("출발일자")
  private LocalDateTime departDate;

  @NameDescription("출발지역")
  private String  departAreaCd;

  @NameDescription("총 버스")
  private int  totBus;

  @NameDescription("49인승 지정버스")
  private int  designatedBus;

  @NameDescription("선박이름")
  private String  cruiseShipName;

  @NameDescription("파트너사 명")
  private String  partnerName;

  @NameDescription("중국 여행사명")
  private String  travalAgencyName;

  @NameDescription("예약일자")
  private LocalDateTime  reservationDate;

  @NameDescription("확정일자")
  private LocalDateTime  confirmDate;

  @NameDescription("취소일자")
  private LocalDateTime  cancelDate;


  //// 삭제 확인 항목 //////
  @NameDescription("답변시간 : assignments 생성시간 ")
  private LocalDateTime answerTime;

  @NameDescription("업체제한시간")
  private LocalDateTime limitTime;

  @NameDescription(" 접수가능 업체 카운트 ")
  private int limitCount;

  @NameDescription("증복확인")
  private int duplicationFlg;


  @NameDescription("접수상태")
  @Enumerated(EnumType.STRING)
  private RequestStatusEnum status;

  @NameDescription("고객 접수단말기(PC, Mobile, Call)")
  private String clientDevice;

  @NameDescription("비고")
  private String comment;




  // 공통 부분
  @LastModifiedDate
  @NameDescription("갱신일")
  private LocalDateTime updateDate;

  @NameDescription("갱신담당자")
  private String updateBy;

  @CreatedDate
  @NameDescription("등록일")
  private LocalDateTime createDate;

  @NameDescription("등록담당자")
  private String createBy;

  @NameDescription("변경내용")
  private String updateContent;
}
