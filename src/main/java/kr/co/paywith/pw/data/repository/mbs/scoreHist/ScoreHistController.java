package kr.co.paywith.pw.data.repository.mbs.scoreHist;

import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.account.CurrentUser;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.user.user.UserInfoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/scoreHist")
@Api(value = "ScoreHistController", description = "쿠폰 API", basePath = "/api/scoreHist")
public class ScoreHistController extends CommonController {

  @Autowired
  ScoreHistRepository scoreHistRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  ScoreHistValidator scoreHistValidator;

  @Autowired
  ScoreHistService scoreHistService;

  @Autowired
  private UserInfoRepository userInfoRepository;

  /**
   * 점수 등록. 관리자가 임의로 점수 등록할 때 사용
   */
  @PostMapping
  public ResponseEntity createScoreHist(
      @RequestBody @Valid ScoreHistDto scoreHistDto,
      Errors errors,
      @CurrentUser Account currentUser) {
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값 체크
    scoreHistValidator.validate(scoreHistDto, errors);
    if (errors.hasErrors()) {
      return badRequest(errors);
    }

    // 입력값을 브랜드 객채에 대입
    ScoreHist scoreHist = modelMapper.map(scoreHistDto, ScoreHist.class);

    // 현재 로그인 유저 설정
    if (currentUser != null) {
      scoreHist.setCreateBy(currentUser.getAccountId());
      scoreHist.setUpdateBy(currentUser.getAccountId());
    }

    scoreHist.setUserInfo(userInfoRepository.findById(scoreHistDto.getUserInfo().getId()).get());

    // 레코드 등록
    ScoreHist newScoreHist = scoreHistService.create(scoreHist);

    ControllerLinkBuilder selfLinkBuilder = linkTo(ScoreHistController.class)
        .slash(newScoreHist.getId());

    URI createdUri = selfLinkBuilder.toUri();
    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    ScoreHistResource scoreHistResource = new ScoreHistResource(newScoreHist);
    scoreHistResource.add(linkTo(ScoreHistController.class).withRel("query-scoreHist"));
    scoreHistResource.add(selfLinkBuilder.withRel("update-scoreHist"));
    scoreHistResource
        .add(new Link("/docs/index.html#resources-scoreHist-create").withRel("profile"));

    return ResponseEntity.created(createdUri).body(scoreHistResource);
  }


  /**
   * 정보취득 (조건별 page )
   */
  @GetMapping
  public ResponseEntity getScoreHists(SearchForm searchForm,
      Pageable pageable,
      PagedResourcesAssembler<ScoreHist> assembler
      , @CurrentUser Account currentUser) {

    // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

    QScoreHist qScoreHist = QScoreHist.scoreHist;

    //
    BooleanBuilder booleanBuilder = new BooleanBuilder();

    // 검색조건 아이디(키)
    if (searchForm.getId() != null) {
      booleanBuilder.and(qScoreHist.id.eq(searchForm.getId()));
    }

    Page<ScoreHist> page = this.scoreHistRepository.findAll(booleanBuilder, pageable);
    var pagedResources = assembler.toResource(page, e -> new ScoreHistResource(e));
    pagedResources.add(new Link("/docs/index.html#resources-scoreHists-list").withRel("profile"));
    pagedResources.add(linkTo(ScoreHistController.class).withRel("create-scoreHist"));
    return ResponseEntity.ok(pagedResources);
  }

  private ResponseEntity badRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }


  /**
   * 정보취득 (1건 )
   */
  @GetMapping("/{id}")
  public ResponseEntity getScoreHist(@PathVariable Integer id,
      @CurrentUser Account currentUser) {

    Optional<ScoreHist> scoreHistOptional = this.scoreHistRepository.findById(id);

    // 고객 정보 체크
    if (scoreHistOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    ScoreHist scoreHist = scoreHistOptional.get();

    // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
    ScoreHistResource scoreHistResource = new ScoreHistResource(scoreHist);
    scoreHistResource.add(new Link("/docs/index.html#resources-scoreHist-get").withRel("profile"));

    return ResponseEntity.ok(scoreHistResource);
  }

}
