package kr.co.paywith.pw.data.repository.mbs.notif;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.validation.Valid;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/notif")
@Api(value = "NotifController", description = "쿠폰 API", basePath = "/api/notif")
public class NotifController extends CommonController {

  @Autowired
  NotifRepository notifRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  NotifValidator notifValidator;

  @Autowired
  NotifService notifService;

  /**
   * 정보 등록
   */
  @PostMapping
  public ResponseEntity createNotif(
      @RequestBody @Valid NotifDto notifDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값 체크
    notifValidator.validate(notifDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값을 브랜드 객채에 대입
    Notif notif = modelMapper.map(notifDto, Notif.class);

    // 현재 로그인 유저 설정
    if (currentUser != null) {
      notif.setCreateBy(currentUser.getAccountId());
      notif.setUpdateBy(currentUser.getAccountId());
    }

    // 레코드 등록
    Notif newNotif = notifService.create(notif);

    ControllerLinkBuilder selfLinkBuilder = linkTo(NotifController.class).slash(newNotif.getId());

    URI createdUri = selfLinkBuilder.toUri();
    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    NotifResource notifResource = new NotifResource(newNotif);
    notifResource.add(linkTo(NotifController.class).withRel("query-notif"));
    notifResource.add(selfLinkBuilder.withRel("update-notif"));
    notifResource.add(new Link("/docs/index.html#resources-notif-create").withRel("profile"));

    return ResponseEntity.created(createdUri).body(notifResource);
  }


  /**
   * 정보취득 (조건별 page )
   */
  @GetMapping
  public ResponseEntity getNotifs(SearchForm searchForm,
      Pageable pageable,
      PagedResourcesAssembler<Notif> assembler
      , @CurrentUser Account currentUser) {

    // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

    QNotif qNotif = QNotif.notif;

    //
    BooleanBuilder booleanBuilder = new BooleanBuilder();

    // 검색조건 아이디(키)
    if (searchForm.getId() != null) {
      booleanBuilder.and(qNotif.id.eq(searchForm.getId()));
    }

    Page<Notif> page = this.notifRepository.findAll(booleanBuilder, pageable);
    var pagedResources = assembler.toResource(page, e -> new NotifResource(e));
    pagedResources.add(new Link("/docs/index.html#resources-notifs-list").withRel("profile"));
    pagedResources.add(linkTo(NotifController.class).withRel("create-notif"));
    return ResponseEntity.ok(pagedResources);
  }

  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }


  /**
   * 정보취득 (1건 )
   */
  @GetMapping("/{id}")
  public ResponseEntity getNotif(@PathVariable Integer id,
      @CurrentUser Account currentUser) {

    Optional<Notif> notifOptional = this.notifRepository.findById(id);

    // 고객 정보 체크
    if (notifOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Notif notif = notifOptional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    NotifResource notifResource = new NotifResource(notif);
    notifResource.add(new Link("/docs/index.html#resources-notif-get").withRel("profile"));

    return ResponseEntity.ok(notifResource);
  }


  //	 /**
//	  * 정보 변경
//	  */
//	 @PutMapping("/{id}")
//	 public ResponseEntity putNotif(@PathVariable Integer id,
//											  @RequestBody @Valid NotifUpdateDto notifUpdateDto,
//											  Errors errors,
//											  @CurrentUser Account currentUser) {
//		  // 입력체크
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//		  // 논리적 오류 (제약조건) 체크
//		  this.notifValidator.validate(notifUpdateDto, errors);
//		  if (errors.hasErrors()) {
//				return badRequest(errors);
//		  }
//
//		  // 기존 테이블에서 관련 정보 취득
//		  Optional<Notif> notifOptional = this.notifRepository.findById(id);
//
//		  // 기존 정보 유무 체크
//		  if (notifOptional.isEmpty()) {
//				// 404 Error return
//				return ResponseEntity.notFound().build();
//		  }
//
//		  // 기존 정보 취득
//		  Notif existNotif = notifOptional.get();
//
//		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
//		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
//		  Notif saveNotif = this.notifService.update(notifUpdateDto, existNotif);
//
//		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
//		  NotifResource notifResource = new NotifResource(saveNotif);
//		  notifResource.add(new Link("/docs/index.html#resources-notif-update").withRel("profile"));
//
//		  // 정상적 처리
//		  return ResponseEntity.ok(notifResource);
//	 }
  @DeleteMapping("/{id}")
  public ResponseEntity removeNotif(@PathVariable Integer id,
      @CurrentUser Account currentUser) {

    Optional<Notif> notifOptional = this.notifRepository.findById(id);

    if (notifOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    // TODO 취소 가능한지 확인
    Notif notif = notifOptional.get();
    if (currentUser.getAdmin() == null
    ) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    if (notif.getSendReqDttm().isBefore(LocalDateTime.now())) {
      // 이미 발송한 메시지는 취소 불가
      return ResponseEntity.badRequest().body(null);
    }

    // 정상적 처리
    return ResponseEntity.ok().build();

  }
}
