package kr.co.paywith.pw.data.repository.mbs.pointRsrvRule;

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
@RequestMapping(value = "/api/pointRsrvRule")
@Api(value = "PointRsrvRuleController", description = "포인트 적립 규칙 API", basePath = "/api/pointRsrvRule")
public class PointRsrvRuleController extends CommonController {

  @Autowired
  PointRsrvRuleRepository pointRsrvRuleRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  PointRsrvRuleValidator pointRsrvRuleValidator;

  @Autowired
  PointRsrvRuleService pointRsrvRuleService;

  /**
   * 정보 등록
   */
  @PostMapping
  public ResponseEntity createPointRsrvRule(
      @RequestBody @Valid PointRsrvRuleDto pointRsrvRuleDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값 체크
    pointRsrvRuleValidator.validate(pointRsrvRuleDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값을 브랜드 객채에 대입
    PointRsrvRule pointRsrvRule = modelMapper.map(pointRsrvRuleDto, PointRsrvRule.class);

    // 현재 로그인 유저 설정
    if (currentUser != null) {
      pointRsrvRule.setCreateBy(currentUser.getAccountId());
      pointRsrvRule.setUpdateBy(currentUser.getAccountId());
    }

    // 레코드 등록
    PointRsrvRule newPointRsrvRule = pointRsrvRuleService.create(pointRsrvRule);

    ControllerLinkBuilder selfLinkBuilder = linkTo(PointRsrvRuleController.class).slash(
        newPointRsrvRule.getId());

    URI createdUri = selfLinkBuilder.toUri();
    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    PointRsrvRuleResource pointRsrvRuleResource = new PointRsrvRuleResource(newPointRsrvRule);
    pointRsrvRuleResource.add(linkTo(PointRsrvRuleController.class).withRel("query-pointRsrvRule"));
    pointRsrvRuleResource.add(selfLinkBuilder.withRel("update-pointRsrvRule"));
    pointRsrvRuleResource
        .add(new Link("/docs/index.html#resources-pointRsrvRule-create").withRel("profile"));

    return ResponseEntity.created(createdUri).body(pointRsrvRuleResource);
  }


  /**
   * 정보취득 (조건별 page )
   */
  @GetMapping
  public ResponseEntity getPointRsrvRules(SearchForm searchForm,
      Pageable pageable,
      PagedResourcesAssembler<PointRsrvRule> assembler
      , @CurrentUser Account currentUser) {

    // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

    QPointRsrvRule qPointRsrvRule = QPointRsrvRule.pointRsrvRule;

    //
    BooleanBuilder booleanBuilder = new BooleanBuilder();

    // 검색조건 아이디(키)
    if (searchForm.getId() != null) {
      booleanBuilder.and(qPointRsrvRule.id.eq(searchForm.getId()));
    }

    Page<PointRsrvRule> page = this.pointRsrvRuleRepository.findAll(booleanBuilder, pageable);
    var pagedResources = assembler.toResource(page, e -> new PointRsrvRuleResource(e));
    pagedResources
        .add(new Link("/docs/index.html#resources-pointRsrvRules-list").withRel("profile"));
    pagedResources.add(linkTo(PointRsrvRuleController.class).withRel("create-pointRsrvRule"));
    return ResponseEntity.ok(pagedResources);
  }

  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }


  /**
   * 정보취득 (1건 )
   */
  @GetMapping("/{id}")
  public ResponseEntity getPointRsrvRule(@PathVariable Integer id,
      @CurrentUser Account currentUser) {

    Optional<PointRsrvRule> pointRsrvRuleOptional = this.pointRsrvRuleRepository.findById(id);

    // 고객 정보 체크
    if (pointRsrvRuleOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    PointRsrvRule pointRsrvRule = pointRsrvRuleOptional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    PointRsrvRuleResource pointRsrvRuleResource = new PointRsrvRuleResource(pointRsrvRule);
    pointRsrvRuleResource
        .add(new Link("/docs/index.html#resources-pointRsrvRule-get").withRel("profile"));

    return ResponseEntity.ok(pointRsrvRuleResource);
  }


  /**
   * 정보 변경
   */
  @PutMapping("/{id}")
  public ResponseEntity putPointRsrvRule(@PathVariable Integer id,
      @RequestBody @Valid PointRsrvRuleUpdateDto pointRsrvRuleUpdateDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    // 입력체크
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 논리적 오류 (제약조건) 체크
    this.pointRsrvRuleValidator.validate(pointRsrvRuleUpdateDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 기존 테이블에서 관련 정보 취득
    Optional<PointRsrvRule> pointRsrvRuleOptional = this.pointRsrvRuleRepository.findById(id);

    // 기존 정보 유무 체크
    if (pointRsrvRuleOptional.isEmpty()) {
      // 404 Error return
      return ResponseEntity.notFound().build();
    }

    // 기존 정보 취득
    PointRsrvRule existPointRsrvRule = pointRsrvRuleOptional.get();

    // 현재 로그인 유저 설정
    if (currentUser != null) {
      existPointRsrvRule.setUpdateBy(currentUser.getAccountId());
    }

    // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
    // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
    PointRsrvRule savePointRsrvRule = this.pointRsrvRuleService.update(pointRsrvRuleUpdateDto,
        existPointRsrvRule);

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    PointRsrvRuleResource pointRsrvRuleResource = new PointRsrvRuleResource(savePointRsrvRule);
    pointRsrvRuleResource
        .add(new Link("/docs/index.html#resources-pointRsrvRule-update").withRel("profile"));

    // 정상적 처리
    return ResponseEntity.ok(pointRsrvRuleResource);
  }
}
