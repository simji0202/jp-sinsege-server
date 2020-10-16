package kr.co.paywith.pw.data.repository;


import java.util.List;
import javax.persistence.*;

import kr.co.paywith.pw.common.NameDescription;


import kr.co.paywith.pw.data.repository.admin.AdminType;
import kr.co.paywith.pw.data.repository.bbs.Board;
import kr.co.paywith.pw.data.repository.partners.PartnerStatus;
import kr.co.paywith.pw.data.repository.requests.RequestStatusEnum;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;


@Data
@ToString
public class SearchForm {

  // 공통
  @NameDescription("Id")
  private Integer id;


  /////// admin //////
  @NameDescription("등록된 관리자 (이사업체) 이름")
  private String adminNm;

  @NameDescription("등록된 관리자 (이사업체) ID ")
  private String adminId;

  @NameDescription("타입(관리자, 여행사, 버스업체 )")
  @Enumerated(EnumType.STRING)
  private AdminType adminType;

  @NameDescription("고객명")
  private String name;

  @NameDescription("전화번호")
  private String phone;

  @NameDescription("담당자이름")
  private String manager;

  ///////// Partners ///////
  @NameDescription(" 회사  ID ")
  private Integer companyId;


  ///////// Partners ///////
  @NameDescription(" 요청  ID ")
  private Integer requestsId;


  @NameDescription(" 파트너사 ID ")
  private Integer partnersId;

  @NameDescription("파트너사 ID ")
  private List<Integer> partnersIds;

  @NameDescription(" 출발 지역")
  private String departAreaCd;

  @NameDescription("출발일자")
  private String  departDate;

  @NameDescription("선박이름")
  private String  cruiseShipName;

  @NameDescription("중국 여행사명")
  private String  travalAgencyName;

  @NameDescription("from신청날짜 ")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime fromCreateDate;

  @NameDescription("to신청날짜")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime toCreateDate;

  @NameDescription("예약상태")
  @Enumerated(EnumType.STRING)
  private RequestStatusEnum reqStateCd;

  @NameDescription("예약상태")
  @Enumerated(EnumType.STRING)
  private List<RequestStatusEnum> reqStateCds;

  @NameDescription("알림날짜")
  private String alarmDate;

  @NameDescription("월별, 일별 구분 코드 1 = 년 , 2 = 월 , 3 = 일일 통계 ")
  private String searchDateType;

  @NameDescription("From 통계 ")
  private String fromDate;

  @NameDescription("To 통계 ")
  private String toDate;

  @NameDescription(" 피드백 ")
  private int feedbackJudge;

  // 포인트 이력 및 입출금 현황
  @NameDescription("업체이름")
  private String partnersName;

  @NameDescription("고객명")
  private String requestsName;

  @NameDescription("고객전화")
  private String requestsPhone;

  @NameDescription(" 전일  ")
  private boolean beforeDay;

  @NameDescription("서비스 지역")
  private String serviceArea;

  @NameDescription("검색 시작 일 ")
  private String searchStartDate;

  @NameDescription("검색 완료 일 ")
  private String searchEndDate;

  /// 파트너사 검색 조건 추가 //
  @NameDescription("주소")
  private String address;

  @NameDescription("상태")
  @Enumerated(EnumType.STRING)
  private PartnerStatus partnerStatus;

  @NameDescription("제목")
  private String title;

  @NameDescription("내용")
  private String content;

  @NameDescription("등록일")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDate;

  @NameDescription("일정이름")
  private String  scheduleName;

  @NameDescription("가이드이름")
  private String  guideName;

  @NameDescription("타입")
  @Enumerated(EnumType.STRING)
  private Board type;



  ///////// Partners ///////
  @NameDescription(" 버스 업체   ID ")
  private Integer agentId;





}
