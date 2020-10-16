package kr.co.paywith.pw.data.repository.bbs;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import com.querydsl.core.BooleanBuilder;
import java.net.URI;
import java.util.Optional;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.common.NameDescription;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.admin.Admin;
import kr.co.paywith.pw.data.repository.admin.AdminAdapter;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/bbs", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@Api(value = "BbsController", description = " 게시판 관리자 API", basePath = "/api/bbs")

public class BbsController {

  @Autowired
  BbsRepository bbsRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  BbsValidator bbsValidator;


  @PostMapping
  public ResponseEntity createBbs(@RequestBody @Valid BbsDto bbsDto,
      Errors errors,
      @CurrentUser Admin currentUser) {

    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    Bbs bbs = modelMapper.map(bbsDto, Bbs.class);

    bbsValidator.validate(bbsDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 등록자 정보 저장
    if (currentUser != null) {
      bbs.setCreateBy(currentUser.getAdminNm());
      bbs.setUpdateBy(currentUser.getAdminNm());
    }

    Bbs newBbs = this.bbsRepository.save(bbs);

    ControllerLinkBuilder selfLinkBuilder = linkTo(BbsController.class).slash(newBbs.getId());

    URI createdUri = selfLinkBuilder.toUri();
    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    BbsResource bbsResource = new BbsResource(bbs);
    bbsResource.add(linkTo(BbsController.class).withRel("query-bbs"));
    bbsResource.add(selfLinkBuilder.withRel("create-bbs"));
    bbsResource.add(new Link("/docs/index.html#resources-bbs-create").withRel("profile"));

    return ResponseEntity.created(createdUri).body(bbsResource);
  }


  @GetMapping
  public ResponseEntity getBbs(SearchForm searchForm,
      Pageable pageable,
      PagedResourcesAssembler<Bbs> assembler,
      @AuthenticationPrincipal AdminAdapter user,
      @CurrentUser Admin currentUser) {

    QBbs qBbs = QBbs.bbs;
    BooleanBuilder booleanBuilder = new BooleanBuilder();


    // 제목
    if (searchForm !=null && searchForm.getTitle() != null ) {
      booleanBuilder.and(qBbs.title.containsIgnoreCase(searchForm.getTitle()));
    }

    // 검색조건
    if (searchForm !=null && searchForm.getContent() != null ) {
      booleanBuilder.and(qBbs.content.containsIgnoreCase(searchForm.getContent()));
    }

    // 검색조건 TYPE
    if (searchForm !=null && searchForm.getType() != null ) {
      booleanBuilder.and(qBbs.type.eq(searchForm.getType()));
    }

    booleanBuilder.and(qBbs.isDelete.eq(false));

    Page<Bbs> page = this.bbsRepository.findAll(booleanBuilder, pageable);
    var pagedResources = assembler.toResource(page, e -> new BbsResource(e));
    pagedResources.add(new Link("/docs/index.html#resources-bbs-list").withRel("profile"));
    if (user != null) {
      pagedResources.add(linkTo(BbsController.class).withRel("create-bbs"));
    }
    return ResponseEntity.ok(pagedResources);
  }


  @GetMapping("/{id}")
  public ResponseEntity getBbs(@PathVariable Integer id,
      @CurrentUser Admin currentUser) {

    Optional<Bbs> optional = this.bbsRepository.findById(id);

    // 고객 정보 체크
    if (optional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Bbs bbs = optional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    BbsResource bbsResource = new BbsResource(bbs);
    bbsResource.add(new Link("/docs/index.html#resources-bbs-get").withRel("profile"));

    return ResponseEntity.ok(bbsResource);
  }


  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }


  @PutMapping("/{id}")
  public ResponseEntity putBbs(@PathVariable Integer id,
      @RequestBody @Valid Bbs bbs,
      Errors errors,
      @CurrentUser Admin currentUser) {
    // 입력체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    BbsDto bbsDto = modelMapper.map(bbs, BbsDto.class);

    // 논리적 오류 (제약조건) 체크
    this.bbsValidator.validate(bbsDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    Optional<Bbs> bbsOptional = this.bbsRepository.findById(id);

    if (bbsOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    Bbs existBbs = bbsOptional.get();
//            if (!existBbs.getManager().equals(currentUser)) {
//                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//            }

    this.modelMapper.map(bbsDto, existBbs);

    //변경자 정보 저장

    if (currentUser != null) {
      bbs.setUpdateBy(currentUser.getAdminNm());
    }

    // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
    // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
    Bbs saveBbs = this.bbsRepository.save(existBbs);

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    BbsResource bbsResource = new BbsResource(saveBbs);
    bbsResource.add(new Link("/docs/index.html#resources-bbs-update").withRel("profile"));

    // 정상적 처리
    return ResponseEntity.ok(bbsResource);

  }


}
