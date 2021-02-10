package kr.co.paywith.pw.data.repository.mbs.msgTemplate;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import java.net.URI;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/msgTemplate")
@Api(value = "MsgTemplateController", description = "쿠폰 API", basePath = "/api/msgTemplate")
public class MsgTemplateController extends CommonController {

  @Autowired
  MsgTemplateRepository msgTemplateRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  MsgTemplateValidator msgTemplateValidator;

  @Autowired
  MsgTemplateService msgTemplateService;

  /**
   * 정보 등록
   */
  @PostMapping
  public ResponseEntity createMsgTemplate(
      @RequestBody @Valid MsgTemplateDto msgTemplateDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값 체크
    msgTemplateValidator.validate(msgTemplateDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값을 브랜드 객채에 대입
    MsgTemplate msgTemplate = modelMapper.map(msgTemplateDto, MsgTemplate.class);

    // 현재 로그인 유저 설정
    if (currentUser != null) {
      msgTemplate.setCreateBy(currentUser.getAccountId());
      msgTemplate.setUpdateBy(currentUser.getAccountId());
    }

    // 레코드 등록
    MsgTemplate newMsgTemplate = msgTemplateService.create(msgTemplate);

    ControllerLinkBuilder selfLinkBuilder = linkTo(MsgTemplateController.class)
        .slash(newMsgTemplate.getId());

    URI createdUri = selfLinkBuilder.toUri();
    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    MsgTemplateResource msgTemplateResource = new MsgTemplateResource(newMsgTemplate);
    msgTemplateResource.add(linkTo(MsgTemplateController.class).withRel("query-msgTemplate"));
    msgTemplateResource.add(selfLinkBuilder.withRel("update-msgTemplate"));
    msgTemplateResource
        .add(new Link("/docs/index.html#resources-msgTemplate-create").withRel("profile"));

    return ResponseEntity.created(createdUri).body(msgTemplateResource);
  }


  /**
   * 정보취득 (조건별 page )
   */
  @GetMapping
  public ResponseEntity getMsgTemplates(SearchForm searchForm,
      Pageable pageable,
      PagedResourcesAssembler<MsgTemplate> assembler
      , @CurrentUser Account currentUser) {

    // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

    QMsgTemplate qMsgTemplate = QMsgTemplate.msgTemplate;

    //
    BooleanBuilder booleanBuilder = new BooleanBuilder();

    // 검색조건 아이디(키)
    if (searchForm.getId() != null) {
      booleanBuilder.and(qMsgTemplate.id.eq(searchForm.getId()));
    }

    Page<MsgTemplate> page = this.msgTemplateRepository.findAll(booleanBuilder, pageable);
    var pagedResources = assembler.toResource(page, e -> new MsgTemplateResource(e));
    pagedResources.add(new Link("/docs/index.html#resources-msgTemplates-list").withRel("profile"));
    pagedResources.add(linkTo(MsgTemplateController.class).withRel("create-msgTemplate"));
    return ResponseEntity.ok(pagedResources);
  }

  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }


  /**
   * 정보취득 (1건 )
   */
  @GetMapping("/{id}")
  public ResponseEntity getMsgTemplate(@PathVariable Integer id,
      @CurrentUser Account currentUser) {

    Optional<MsgTemplate> msgTemplateOptional = this.msgTemplateRepository.findById(id);

    // 고객 정보 체크
    if (msgTemplateOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    MsgTemplate msgTemplate = msgTemplateOptional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    MsgTemplateResource msgTemplateResource = new MsgTemplateResource(msgTemplate);
    msgTemplateResource
        .add(new Link("/docs/index.html#resources-msgTemplate-get").withRel("profile"));

    return ResponseEntity.ok(msgTemplateResource);
  }


  /**
   * 정보 변경
   */
  @PutMapping("/{id}")
  public ResponseEntity putMsgTemplate(@PathVariable Integer id,
      @RequestBody @Valid MsgTemplateUpdateDto msgTemplateUpdateDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    // 입력체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 논리적 오류 (제약조건) 체크
    this.msgTemplateValidator.validate(msgTemplateUpdateDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 기존 테이블에서 관련 정보 취득
    Optional<MsgTemplate> msgTemplateOptional = this.msgTemplateRepository.findById(id);

    // 기존 정보 유무 체크
    if (msgTemplateOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    // 기존 정보 취득
    MsgTemplate existMsgTemplate = msgTemplateOptional.get();

    // 현재 로그인 유저 설정
    if (currentUser != null) {
      existMsgTemplate.setUpdateBy(currentUser.getAccountId());
    }

    // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
    // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
    MsgTemplate saveMsgTemplate = this.msgTemplateService
        .update(msgTemplateUpdateDto, existMsgTemplate);

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    MsgTemplateResource msgTemplateResource = new MsgTemplateResource(saveMsgTemplate);
    msgTemplateResource
        .add(new Link("/docs/index.html#resources-msgTemplate-update").withRel("profile"));

    // 정상적 처리
    return ResponseEntity.ok(msgTemplateResource);
  }
}
