package kr.co.paywith.pw.data.repository.requests;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;

import kr.co.paywith.pw.common.AppProperties;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.common.ExcelConstant;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.component.Scheduler;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.admin.AdminAdapter;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.agents.Agents;
import kr.co.paywith.pw.data.repository.agents.AgentsRepository;
import kr.co.paywith.pw.data.repository.message.MessageService;
import kr.co.paywith.pw.data.repository.partners.RequestsListResource;
import kr.co.paywith.pw.data.repository.requestsBus.QRequestBus;
import kr.co.paywith.pw.data.repository.requestsBus.RequestBus;
import kr.co.paywith.pw.data.repository.requestsBus.RequestBusRepository;
import kr.co.paywith.pw.mapper.StatisticsMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.net.URI;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/requests", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@Api(value = "RequestsController", description = "고객 상담 신청 API")
public class RequestsController {

  @Autowired
  RequestsRepository requestsRepository;


  @Autowired
  RequestBusRepository requestBusRepository;


  @Autowired
  Scheduler scheduler;

  @Autowired
  RequestsValidator requestsValidator;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  RequestsService requestsService;

  @Autowired
  MessageService messageService;

  @Autowired
  AppProperties appProperties;

  @Autowired
  StatisticsMapper statisticsMapper;


  @PostMapping
  private ResponseEntity insertRequests(@RequestBody @Valid RequestsDto requestsDto,
                                        Errors errors,
                                        @CurrentUser Admin currentUser) {
    // 입력 값 체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 논리적 오류 (제약조건) 체크
    requestsValidator.validate(requestsDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    Requests newRequests = this.requestsService.create(requestsDto, currentUser);

    ControllerLinkBuilder selfLinkBuilder = linkTo(RequestsController.class)
            .slash(newRequests.getId());
    URI createdUri = selfLinkBuilder.toUri();

    RequestsResource requestsResource = new RequestsResource(newRequests);
    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    requestsResource.add(linkTo(RequestsController.class).withRel("query-requests"));
    requestsResource.add(selfLinkBuilder.withRel("update-requests"));
    requestsResource.add(new Link("/docs/index.html#resources-requests-create").withRel("profile"));

    return ResponseEntity.created(createdUri).body(requestsResource);

  }


  @GetMapping
  public ResponseEntity getRequestss(SearchForm searchForm,
                                     Pageable pageable,
                                     PagedResourcesAssembler<RequestsList> assembler
      , @AuthenticationPrincipal AdminAdapter user
      , @CurrentUser Admin currentUser) {

    // 인증상태의 유저 정보 확인
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User princpal = (User) authentication.getPrincipal();

    QRequests qRequests = QRequests.requests;

    BooleanBuilder booleanBuilder = new BooleanBuilder();

    // 회사 아이디
    if ( searchForm.getCompanyId() != null && searchForm.getCompanyId() > 0) {
      booleanBuilder.and(qRequests.partners.company.id.eq(searchForm.getCompanyId()));
    }

    // 파트너사
    if ( searchForm.getPartnersId() != null && searchForm.getPartnersId() > 0) {
      booleanBuilder.and(qRequests.partners.id.eq(searchForm.getPartnersId()));
    }

    // 출발일자
    if (searchForm.getDepartDate() != null) {
      booleanBuilder.and(qRequests.departDate.eq(searchForm.getDepartDate()));
    }

    // 출발지역
    if (searchForm.getDepartAreaCd() != null) {
      booleanBuilder.and(qRequests.departAreaCd.eq(searchForm.getDepartAreaCd()));
    }

    // 예약상태 (복수)
    if (searchForm.getReqStateCds() != null) {
      booleanBuilder.and(qRequests.reqStateCd.in(searchForm.getReqStateCds()));
    }

    // 검색조건 예약일
    if (searchForm.getFromCreateDate() != null || searchForm.getToCreateDate() != null) {

        if (searchForm.getFromCreateDate() != null && searchForm.getToCreateDate() != null) {
            booleanBuilder.and(qRequests.createDate
                    .between(searchForm.getFromCreateDate(), searchForm.getToCreateDate()));

        } else if (searchForm.getFromCreateDate() != null) {
            booleanBuilder.and(qRequests.createDate.goe(searchForm.getFromCreateDate()));

        } else if (searchForm.getToCreateDate() != null) {
            booleanBuilder.and(qRequests.createDate.loe(searchForm.getToCreateDate()));
        }
    }


    // 선박이름
    if (searchForm.getCruiseShipName() != null) {
      booleanBuilder.and(qRequests.cruiseShipName.containsIgnoreCase(searchForm.getCruiseShipName()));
    }

    // 중국 여행사명
    if (searchForm.getTravalAgencyName() != null) {
      booleanBuilder.and(qRequests.travalAgencyName.containsIgnoreCase(searchForm.getTravalAgencyName()));
    }

    Page<RequestsList> page = this.requestsRepository.findAllRequestsList(booleanBuilder, pageable);

    var pagedResources = assembler.toResource(page, e -> new RequestsListResource(e));
    pagedResources.add(new Link("/docs/index.html#resources-requestss-list").withRel("profile"));
    if (user != null) {
      pagedResources.add(linkTo(RequestsController.class).withRel("create-requests"));
    }
    return ResponseEntity.ok(pagedResources);
  }



  @GetMapping("/bus")
  public ResponseEntity getRequestsBus(SearchForm searchForm,
                                        Pageable pageable,
                                        PagedResourcesAssembler<RequestsList> assembler
          , @AuthenticationPrincipal AdminAdapter user
          , @CurrentUser Admin currentUser) {

    // 인증상태의 유저 정보 확인
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User princpal = (User) authentication.getPrincipal();

    QRequests qRequests = QRequests.requests;

    BooleanBuilder booleanBuilder = new BooleanBuilder();


    // 회사 아이디
    if ( searchForm.getAgentId() != null && searchForm.getAgentId() > 0) {
      booleanBuilder.and(qRequests.requestBuses.any().agent.id.eq(searchForm.getAgentId()));
    }

    Page<RequestsList> page = this.requestsRepository.findAllRequestsList(booleanBuilder, pageable);


    var pagedResources = assembler.toResource(page, e -> new RequestsListResource(e));
    pagedResources.add(new Link("/docs/index.html#resources-requestss-list").withRel("profile"));
    if (user != null) {
      pagedResources.add(linkTo(RequestsController.class).withRel("requests"));
    }
    return ResponseEntity.ok(pagedResources);

  }

  @GetMapping("/findRequests")
  public ResponseEntity getFindRequests(SearchForm searchForm,
                                        Pageable pageable,
                                        PagedResourcesAssembler<RequestsList> assembler
      , @AuthenticationPrincipal AdminAdapter user
      , @CurrentUser Admin currentUser) {

    // 인증상태의 유저 정보 확인
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User princpal = (User) authentication.getPrincipal();

    QRequests qRequests = QRequests.requests;

    BooleanBuilder booleanBuilder = new BooleanBuilder();


    Page<RequestsList> page = this.requestsRepository.findAllRequestsList(booleanBuilder, pageable);
    var pagedResources = assembler.toResource(page, e -> new RequestsListResource(e));
    pagedResources.add(new Link("/docs/index.html#resources-requestss-list").withRel("profile"));
    if (user != null) {
      pagedResources.add(linkTo(RequestsController.class).withRel("requests"));
    }
    return ResponseEntity.ok(pagedResources);

  }

  @GetMapping("/{id}/message")
  public ResponseEntity getRequestsMessage(@PathVariable Integer id,
                                           @CurrentUser Admin currentUser) {

    Optional<Requests> requestsOptional = this.requestsRepository.findById(id);

    // 접수 정보 체크
    if (requestsOptional == null || requestsOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Requests requests = requestsOptional.get();

    this.requestsService.sendMessagePartners(requests);

    return ResponseEntity.ok().build();
  }


  @GetMapping("/{id}")
  public ResponseEntity getRequests(@PathVariable Integer id,
                                    @CurrentUser Admin currentUser) {

    Optional<Requests> requestsOptional = this.requestsRepository.findById(id);

    // 접수 정보 체크
    if (requestsOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Requests requests = requestsOptional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    RequestsResource requestsResource = new RequestsResource(requests);
    requestsResource.add(new Link("/docs/index.html#resources-requests-get").withRel("profile"));

    return ResponseEntity.ok(requestsResource);
  }



//  @GetMapping("/{id}/assignments")
//  public ResponseEntity getAssignments(SearchForm searchForm,
//                                       @PathVariable Integer id,
//                                       @CurrentUser Admin currentUser) {
//
//    Optional<Requests> requestsOptional = this.requestsRepository.findById(id);
//
//    // 접수 정보 체크
//    if (requestsOptional == null || requestsOptional.isEmpty()) {
//      return ResponseEntity.notFound().build();
//    }
//
//    Requests requests = requestsOptional.get();
//
//    QPartners qPartners = QPartners.partners;
//
//    QPartnerClosedates qPartnerClosedates = QPartnerClosedates.partnerClosedates;
//
//    BooleanBuilder booleanBuilder = new BooleanBuilder();
//
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
//
//    // 마감일 설정
//    String wishMoveDate = requests.getWishMoveDate();
//    List list = this.statisticsMapper.partnerClosedates(wishMoveDate);
//
//    if (list != null && list.size() > 0) {
//      booleanBuilder.and(qPartners.id.notIn(list));
//    }
//
//    // 	JPAExpressions.select(qPartnerClosedates.).from(qPartnerClosedates).where(qPartnerClosedates.)
//
//    // 이사 서비스
//    PartnersMoveServiceEnum partnersMoveServic = requests.getMoveType();
//
//    if (partnersMoveServic != null) {
//      booleanBuilder.and(qPartners.partnersMoveService.any().in(partnersMoveServic));
//    }
//
//    // 신규가입, 영업승인, 잔앤부족
//    booleanBuilder.and(
//        qPartners.status.in(List.of(PartnerStatus.신규가입, PartnerStatus.영업승인, PartnerStatus.잔액부족)));
//
//    List<PartnersListDto> partnersList = this.requestsRepository
//        .findAllMessagePartnersList(booleanBuilder);
//
//    return ResponseEntity.ok(partnersList);
//  }


//  @GetMapping("/{id}/createAssignments")
//  public ResponseEntity createAssignments(SearchForm searchForm,
//                                          @PathVariable Integer id,
//                                          @CurrentUser Admin currentUser) {
//
//    Optional<Requests> requestsOptional = this.requestsRepository.findById(id);
//
//    // 접수 정보 체크
//    if (requestsOptional == null || requestsOptional.isEmpty()) {
//      return ResponseEntity.notFound().build();
//    }
//
//    Requests requests = requestsOptional.get();
//
//    QPartners qPartners = QPartners.partners;
//    BooleanBuilder booleanBuilder = new BooleanBuilder();
//
//    // 이사 업체 아이디 검색
//    if (searchForm.getPartnersIds() != null) {
//      booleanBuilder.and(qPartners.id.in(searchForm.getPartnersIds()));
//    }
//
//    List<PartnersListDto> partnersList = this.requestsRepository
//        .findAllMessagePartnersList(booleanBuilder);
//
//    this.requestsService.createAssignments(requests, partnersList);
//
//    return ResponseEntity.ok().build();
//  }

  @GetMapping("/{id}/close")
  public ResponseEntity getRequestClose(@PathVariable Integer id,
                                        @CurrentUser Admin currentUser) {

    Optional<Requests> requestsOptional = this.requestsRepository.findById(id);

    // 접수 정보 체크
    if (requestsOptional == null || requestsOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Requests requests = requestsOptional.get();

    this.requestsService.requestsClose(requests);

    return ResponseEntity.ok().build();
  }


  @GetMapping("/{id}/duplicationFlg")
  public ResponseEntity getDuplicationFlg(@PathVariable Integer id,
                                          @CurrentUser Admin currentUser) {

    Optional<Requests> requestsOptional = this.requestsRepository.findById(id);

    // 접수 정보 체크
    if (requestsOptional == null || requestsOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Requests requests = requestsOptional.get();

    this.requestsRepository.save(requests);

    return ResponseEntity.ok().build();
  }



  @GetMapping("/encryptor")
  public ResponseEntity getRequestsEncryptor(SearchForm searchForm,
                                             @AuthenticationPrincipal AdminAdapter user,
                                             @CurrentUser Admin currentUser) {
    Integer decryptId = 0;

    if (searchForm.getRequestsName() != null) {
      String id = appProperties.decryptionUsing(searchForm.getRequestsName());

      try {
        decryptId = Integer.valueOf(id);
      } catch (NumberFormatException nfe) {
        nfe.printStackTrace();
      }
    }

    Optional<Requests> requestsOptional = this.requestsRepository.findById(decryptId);
    ;

    // 접수 정보 체크
    if (requestsOptional == null) {
      return ResponseEntity.notFound().build();
    }

    Requests requests = requestsOptional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    RequestsResource requestsResource = new RequestsResource(requests);
    requestsResource.add(new Link("/docs/index.html#resources-requests-get").withRel("profile"));

    return ResponseEntity.ok(requestsResource);
  }



  /**
   *  예약 사항
   */
  @GetMapping("/requestsInfo")
  public ResponseEntity getRequestsInfo(SearchForm searchForm,
                                        @AuthenticationPrincipal AdminAdapter user,
                                        @CurrentUser Admin currentUser) throws ParseException {

    // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

//    List<RequestsInfo> requestsInfoList =  null;
    List<RequestsInfo> requestsInfoList =  this.statisticsMapper.requestInfo(searchForm);

    return ResponseEntity.ok(requestsInfoList);
  }



//  @GetMapping("/excel")
//  public ModelAndView getExcelxls(SearchForm searchForm,
//                                  @AuthenticationPrincipal AdminAdapter user,
//                                  @CurrentUser Admin currentUser) {
//
//    List<RequestsListExcel> requestsListExcel = requestsService.getRequestsListExcel(searchForm);
//
//    ArrayList body = new ArrayList();
//    List bodes = new ArrayList();
//
//    for (RequestsListExcel requestsListExcel1 : requestsListExcel) {
//
//      body.add(Arrays.asList(
//          requestsListExcel1.getId()
////          requestsListExcel1.getName(),
////          requestsListExcel1.getPhone(),
////          requestsListExcel1.getDepartAddress(),
////          requestsListExcel1.getArrivalAddress(),
////          requestsListExcel1.getWishMoveDate(),
////          requestsListExcel1.getMoveType(),
////          requestsListExcel1.getSpace(),
////          requestsListExcel1.getCreateDate(),
////          requestsListExcel1.getStep(),
////          requestsListExcel1.getStatus(),
////          requestsListExcel1.getAgents(),
////          requestsListExcel1.getInflowPath(),
////          requestsListExcel1.getRefererUrl()
//      ));
//    }
//    return new ModelAndView("excelXlsView", initExcelData(body));
//  }

  @PutMapping("/{id}/statusUpdate")
  public ResponseEntity putStatusUpdate(@PathVariable Integer id,
                                        @RequestBody @Valid Requests requests,
                                        @CurrentUser Admin currentUser) {

    if (requests.getReqStateCd() != null&& (
            requests.getReqStateCd().equals(RequestStatusEnum.예약신청)
                    || requests.getReqStateCd().equals(RequestStatusEnum.예약신청완료)
                    || requests.getReqStateCd().equals(RequestStatusEnum.예약신청변경)
                    || requests.getReqStateCd().equals(RequestStatusEnum.버스수배중)
                    || requests.getReqStateCd().equals(RequestStatusEnum.버스수배완료)
                    || requests.getReqStateCd().equals(RequestStatusEnum.예약확정)
                    || requests.getReqStateCd().equals(RequestStatusEnum.예약취소)
    )) {

      RequestStatusEnum status = requests.getReqStateCd();
      // 상담신청에 대한 상태 변경ㅈ
      this.requestsRepository.updateRequestsStatus(id, status);
    }

    // 정상적 처리
    return ResponseEntity.ok().build();

  }

  @PutMapping("/{id}")
  public ResponseEntity putRequests(@PathVariable Integer id,
                                    @RequestBody @Valid RequestsUpdateDto requestsUpdateDto,
                                    Errors errors,
                                    @CurrentUser Admin currentUser) {
    // 입력체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 논리적 오류 (제약조건) 체크
    this.requestsValidator.validate(requestsUpdateDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    Optional<Requests> requestsOptional = this.requestsRepository.findById(id);

    if (requestsOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    Requests existRequests = requestsOptional.get();
//			if (!existingEvent.getManager().equals(currentUser)) {
//				return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//			}


    // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
    // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
    Requests saveRequests = this.requestsService.update(requestsUpdateDto, existRequests, currentUser);

    //  메세지 관련 정보 취득
//    if (requestsSendMessage != null && saveRequests.getStep() < 6) {
//      // 메세지 관련 정보 설정
// //     requestsSendMessage.setStep(saveRequests.getStep());
//      requestsSendMessage.setStatus(saveRequests.getStatus());
//
//      requestsSendMessage.setSendFlgStepDate(targetDateTime);
//
//      requestsSendMessageRepository.save(requestsSendMessage);
//
//    } else if (requestsSendMessage != null && saveRequests.getStep() == 6
//        && saveRequests.getAnswerTime() == null) {
//
//      // 메세지 관련 정보 설정
//      requestsSendMessage.setStep(saveRequests.getStep());
//      requestsSendMessage.setStatus(saveRequests.getStatus());
//
//      requestsSendMessage.setLimitFlgDate(targetDateTime);
//
//      requestsSendMessageRepository.save(requestsSendMessage);
//
//    }

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    RequestsResource requestsResource = new RequestsResource(saveRequests);
    requestsResource.add(new Link("/docs/index.html#resources-requests-update").withRel("profile"));

    // 정상적 처리
    return ResponseEntity.ok(requestsResource);

  }

  private Map<String, Object> initExcelData(List body) {
    Map<String, Object> map = new HashMap<>();
    map.put(ExcelConstant.FILE_NAME, "requests");
    map.put(ExcelConstant.HEAD, Arrays
        .asList("NO", "고객명", "전화번호", "출발지", "도착지", "이사날짜", "이사종류", "평형", "신청날짜", "고객접수단계", "상담상태",
            "Agents", "유입경로", "Reference"));
    map.put(ExcelConstant.BODY, body);

    return map;
  }

  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }

}
