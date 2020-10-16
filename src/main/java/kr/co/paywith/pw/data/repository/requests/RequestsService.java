package kr.co.paywith.pw.data.repository.requests;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.querydsl.core.BooleanBuilder;


import kr.co.paywith.pw.common.AppProperties;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.message.MessageService;
import kr.co.paywith.pw.data.repository.message.MsgRuleCd;
import kr.co.paywith.pw.data.repository.partners.Partners;
import kr.co.paywith.pw.data.repository.partners.PartnersListDto;
import kr.co.paywith.pw.data.repository.partners.PartnersRepository;
import kr.co.paywith.pw.data.repository.partners.QPartners;
import kr.co.paywith.pw.data.repository.requestsBus.BusStateEnum;
import kr.co.paywith.pw.data.repository.requestsBus.BusTypeEnum;
import kr.co.paywith.pw.data.repository.requestsBus.RequestBus;
import kr.co.paywith.pw.data.repository.requestsBus.RequestBusRepository;
import kr.co.paywith.pw.mapper.StatisticsMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RequestsService {

  @Autowired
  RequestsRepository requestsRepository;

  @Autowired
  PartnersRepository partnersRepository;

  @Autowired
  RequestBusRepository requestBusRepository;


  @Autowired
  ModelMapper modelMapper;

  @Autowired
  StatisticsMapper statisticsMapper;

  @Autowired
  MessageService messageService;


  @Autowired
  AppProperties appProperties;


  @Transactional
  public Requests create(RequestsDto requestsDto, Admin currentUser) {

    Requests requests = modelMapper.map(requestsDto, Requests.class);

    if (currentUser != null) {
      requests.setCreateBy(currentUser.getAdminNm());
      requests.setUpdateBy(currentUser.getAdminNm());

    }

    Requests newRequests = this.requestsRepository.save(requests);

    // 버스 자동 추가
    int count = 0;
    if  ( requestsDto.getSeaterBus49() > 0 ) {
      for ( int i = 0 ; i < requestsDto.getTotBus() ; i++ ) {
        RequestBus requestBus = new RequestBus();
        if ( requestsDto.getSeaterBus49() > count ) {
          // 49인승 추가
          requestBus.setBusTypeCd(BusTypeEnum.BUS49);
        } else  {
          // 45인승이상 추가
          requestBus.setBusTypeCd(BusTypeEnum.BUS45);
        }

        count ++;
        requestBus.setBusSeq(count);
        requestBus.setBusStateCd(BusStateEnum.미정);
        requestBus.setRequests(newRequests);
        this.requestBusRepository.save(requestBus);
      }
    }





    return newRequests;
  }


  @Transactional
  public Requests update(RequestsUpdateDto requestsUpdateDto, Requests requests, Admin currentUser) {


    if (currentUser != null) {
      requests.setCreateBy(currentUser.getAdminNm());
      requests.setUpdateBy(currentUser.getAdminNm());
    }

    if ( requests.getRequestBuses() != null ) {
      requests.getRequestBuses().clear();
      requests.getRequestBuses().addAll((requestsUpdateDto.getRequestBuses()));
    }


    // 버스 자동 추가
    int totalCount = 0, totBusDecide=0, seaterBusDecide=0;

    if ( requestsUpdateDto.getRequestBuses() != null && requestsUpdateDto.getRequestBuses().size() > 0 ) {
       //  booking.setTourNumber(bookingUpdateDto.getBookingMember().size());

      List<RequestBus> requestBuseList = requestsUpdateDto.getRequestBuses();
      if (requestBuseList != null && requestBuseList.size() > 0) {

        for (RequestBus requestBus : requestBuseList) {
          if ( requestBus.getBusTypeCd().equals(BusTypeEnum.BUS49)) {

            if (requestBus.getBusStateCd().equals(BusStateEnum.확정)) {
              seaterBusDecide++;
            }
          } else {
            if (requestBus.getBusStateCd().equals(BusStateEnum.확정)) {
              totBusDecide++;
            }
          }

          requestBus.setRequests(requests);
          this.requestBusRepository.save(requestBus);
        }
      }
    }




    if ( requests.getTotBus() != requestsUpdateDto.getTotBus() ) {
      String message = " 버스 요청 수를 [" + requests.getTotBus() + "]에서 [" + requestsUpdateDto.getTotBus() + "] (으로/로) 변경하였습니다";
      String updateContent = getUpdateLog(currentUser, message);
      requests.setUpdateContent(getNullCheck(requests.getUpdateContent()) + updateContent);
    }

    if ( requests.getSeaterBus49() != requestsUpdateDto.getSeaterBus49() ) {
      String message = " 49인승 버스 요청 수를 [" + requests.getSeaterBus49() + "]에서 [" + requestsUpdateDto.getSeaterBus49() + "] (으로/로) 변경하였습니다";
      String updateContent = getUpdateLog(currentUser, message);
      requests.setUpdateContent(getNullCheck(requests.getUpdateContent()) + updateContent);
    }

    requests.setTravalAgencyName(requestsUpdateDto.getTravalAgencyName());
    requests.setCruiseShipName(requestsUpdateDto.getCruiseShipName());
    requests.setTotBusDecide(totBusDecide + seaterBusDecide);
    requests.setSeaterBusDecide(seaterBusDecide);
    requests.setTotBus(requestsUpdateDto.getTotBus());
    requests.setSeaterBus49(requestsUpdateDto.getSeaterBus49());


 //   String message = " 대표자 정보를  " + existBooking.getRepresentative().getName() + "에서 " + bookingUpdateDto.getRepresentative().getName() + " (으로/로) 변경하였습니다";

    Requests newRequests = this.requestsRepository.save(requests);

    return newRequests;
  }


//  @Transactional
//  public List<RequestsListExcel> getRequestsListExcel(SearchForm searchForm) {
//
//    List<RequestsListExcel> resultList = statisticsMapper.excelRequestsList(searchForm);
//
//    return resultList;
//  }

  /**
   * 고객의 접수단계 6단계 완료후 고객에게 메세지 전송
   * @param requests
   */
  @Transactional
  public void sendMessagePartners(Requests requests) {

    QPartners qPartners = QPartners.partners;


    BooleanBuilder booleanBuilder = new BooleanBuilder();

//    //도착 서비스 지역 Code
//    PartnersBusinessAreasEnum arrivalPartnersBusinessAreas = requests.getArrivalAddressCode();
//    if (arrivalPartnersBusinessAreas != null) {
//      booleanBuilder
//          .and(qPartners.arrivalPartnersBusinessAreas.any().in(arrivalPartnersBusinessAreas));
//    }
//
//    //출발 서비스 지역 Code
//    PartnersBusinessAreasEnum departPartnersBusinessAreas = requests.getDepartAddressCode();
//    if (departPartnersBusinessAreas != null) {
//      booleanBuilder
//          .and(qPartners.departPartnersBusinessAreas.any().in(departPartnersBusinessAreas));
//    }

    // 마감일 설정
//    String wishMoveDate = requests.getWishMoveDate();
//    List list = this.statisticsMapper.partnerClosedates(wishMoveDate);
//
//    if (list != null && list.size() > 0) {
//      booleanBuilder.and(qPartners.id.notIn(list));
//    }

    // 	JPAExpressions.select(qPartnerClosedates.).from(qPartnerClosedates).where(qPartnerClosedates.)

    // 이사 서비스
//    PartnersMoveServiceEnum partnersMoveServic = requests.getMoveType();
//
//    if (partnersMoveServic != null) {
//      booleanBuilder.and(qPartners.partnersMoveService.any().in(partnersMoveServic));
//    }

    // 사진등록 및 방문견적요청에 따라서 상태코드 설정
//   // if (requests.getOpt6() != null && requests.getOpt6().contains(RequestStatusEnum.방문견적요청.toString())) {
//    if (requests.getOpt6() != null && requests.getOpt6().contains(RequestStatusEnum.방문신청완료.toString())) {
//     // 방문견적요청 상태 코드
//      requests.setStatus(RequestStatusEnum.방문신청완료);
//    } else {
//      // 사진등록 상태 코드
//      requests.setStatus(RequestStatusEnum.사진신청완료);

    this.requestsRepository.save(requests);

    // 신규가입, 영업승인, 잔앤부족
 //   booleanBuilder.and(qPartners.status.in(List.of(PartnerStatus.신규가입, PartnerStatus.영업승인, PartnerStatus.잔액부족)));

    List<PartnersListDto> partnersList = this.requestsRepository
        .findAllMessagePartnersList(booleanBuilder);

    // 관련 RequestAssignments 생성 및 업체 메세지 송신
    createAssignments(requests, partnersList);

    String encryptedPassword = appProperties.encryptionUsing(requests.getId().toString());


//    // 사진등록 및 방문견적요청에 따라서 메세지 전송
//    if (requests.getOpt6() != null && requests.getOpt6().contains(RequestStatusEnum.방문신청완료.toString())) {
//      // 이사 접수 완료 고객에게 메세지 송신
//      this.sendRequestsMessage(requests, MsgRuleCd.방문견적요청_고객, encryptedPassword);
//    } else {
//      // 이사 접수 완료 고객에게 메세지 송신
//      this.sendRequestsMessage(requests, MsgRuleCd.견적신청완료_고객, encryptedPassword);
//    }


  }

  /**
   * 관련 RequestAssignments 생성 및 업체 메세지 송신
   *
   * @param requests
   * @param partnersList
   */
  public void createAssignments(Requests requests, List<PartnersListDto> partnersList ) {

    if (partnersList != null && partnersList.size() > 0) {

//      // 관련 RequestAssignments 생성
//      for (PartnersListDto partnersListDto : partnersList) {
//
//        // 견적, 상담관련 레코드 생성
//        RequestAssignments requestAssignments = new RequestAssignments();
//
//        // 견적 신청 설정
//        requestAssignments.setRequests(requests);
//
//        // 이사업체 설정
//        Partners partners = partnersRepository.findById(partnersListDto.getId()).orElse(null);
//        requestAssignments.setPartners(partners);
//
//        // 사진등록 및 방문견적요청에 따라서 메세지 전송
//        if (requests.getOpt6() != null && requests.getOpt6().contains(RequestStatusEnum.방문신청완료.toString())) {
//          // 방문요청
//          requestAssignments.setStatus(RequestAssignmentsStatusEnum.방문요청);
//          // 업체 메세지 송신
//          this.sendPartnersMessage(partners, requests, MsgRuleCd.방문견적요청_업체);
//
//        } else {
//          // 견적요청
//          requestAssignments.setStatus(RequestAssignmentsStatusEnum.견적요청);
//          // 업체 메세지 송신
//          this.sendPartnersMessage(partners, requests, MsgRuleCd.견적신청완료_업체);
//
//        }
//        requestAssignmentsRepository.save(requestAssignments);
//      }
    }
  }


  public void requestsClose(Requests requests) {

    if (requests != null) {

      String encryptedPassword = appProperties.encryptionUsing(requests.getId().toString());
      this.sendRequestsMessage(requests, MsgRuleCd.접수대기_고객, encryptedPassword);
    }
  }


  // 업체 메세지 송신
  private void sendPartnersMessage(Partners partners, Requests requests, MsgRuleCd msgRuleCd) {

    Map<String, Object> map = new HashMap<>();
    map.put("P_NAME", partners.getName());
//    map.put("WISH_MOVE_DATE", requests.getWishMoveDate());
//    map.put("R_NAME", requests.getName());
//    if ( requests.getOpt6() != null ) {
//      map.put("VISIT_DATE", getVisitDate(requests.getOpt6() ));
//    }
//    map.put("DEPART", requests.getDepartAddress());
//    map.put("ARRIVAL", requests.getArrivalAddress());
//    map.put("LIMIT_TIME", requests.getLimitTimeDisplay());

    map.put("URL", "http://admin.zimshot.com/");

    this.messageService.sendMsgByRule(partners.getPhone(), null, msgRuleCd, map);
  }

  // 고객 메세지 송신
  private void sendRequestsMessage(Requests requests, MsgRuleCd msgRuleCd,
      String encryptedPassword) {

    Map<String, Object> map = new HashMap<>();
//    map.put("R_NAME", requests.getName());
//    map.put("STEP", requests.getStep());
//    if ( requests.getOpt6() != null ) {
//      map.put("VISIT_DATE", getVisitDate(requests.getOpt6() ));
//    }
//    map.put("LIMIT_TIME", requests.getLimitTimeDisplay());
    map.put("URL", "http://www.zimshot.com/" + encryptedPassword);

   // this.messageService.sendMsgByRule(requests.getPhone(), null, msgRuleCd, map);

  }


  private String getVisitDate(String str) {

    JsonParser parser = new JsonParser();
    JsonElement element = parser.parse(str);
    if (element != null ) {
      String visitDate = element.getAsJsonObject().get("visitDate").getAsString();
      String visitTime = element.getAsJsonObject().get("visitTime").getAsString();

      return  visitDate +" "+ visitTime;
    }

    return "";
  }




  private String getUpdateLog(Admin currentUser, String message) {
    SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
    String format_time1 = format1.format (System.currentTimeMillis());

    return format_time1 + " "  + currentUser.getAdminNm() + "의 (" + currentUser.getAdminId()  + ")님이 " + message + "\r\n";
  }

  /**
   *  Null 객체 체크
   * @param str
   * @return
   */
  public String getNullCheck (String str ) {

    if ( str == null ) return "";
    if ( str.equals("") ) return  "";
    if ( str.equals("null")) return  "";

    return  str;
  }

}
