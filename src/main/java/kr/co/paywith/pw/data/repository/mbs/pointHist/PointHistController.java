package kr.co.paywith.pw.data.repository.mbs.pointHist;

import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.mbs.abs.CommonController;
import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import kr.co.paywith.pw.common.ErrorsResource;import org.modelmapper.ModelMapper;
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
@RequestMapping(value = "/api/pointHist")
@Api(value = "PointHistController", description = "쿠폰 API", basePath = "/api/pointHist")
public class PointHistController extends CommonController {

	 @Autowired
	 PointHistRepository pointHistRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 PointHistValidator pointHistValidator;

	 @Autowired
	 PointHistService pointHistService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createPointHist(
				@RequestBody @Valid PointHistDto pointHistDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  pointHistValidator.validate(pointHistDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  PointHist pointHist = modelMapper.map(pointHistDto, PointHist.class);

		  // 레코드 등록
		  PointHist newPointHist = pointHistService.create(pointHist);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(PointHistController.class).slash(newPointHist.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PointHistResource pointHistResource = new PointHistResource(newPointHist);
		  pointHistResource.add(linkTo(PointHistController.class).withRel("query-pointHist"));
		  pointHistResource.add(selfLinkBuilder.withRel("update-pointHist"));
		  pointHistResource.add(new Link("/docs/index.html#resources-pointHist-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(pointHistResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getPointHists(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<PointHist> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QPointHist qPointHist = QPointHist.pointHist;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qPointHist.id.eq(searchForm.getId()));
		  }


		  Page<PointHist> page = this.pointHistRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new PointHistResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-pointHists-list").withRel("profile"));
		  pagedResources.add(linkTo(PointHistController.class).withRel("create-pointHist"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getPointHist(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<PointHist> pointHistOptional = this.pointHistRepository.findById(id);

		  // 고객 정보 체크
		  if (pointHistOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  PointHist pointHist = pointHistOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PointHistResource pointHistResource = new PointHistResource(pointHist);
		  pointHistResource.add(new Link("/docs/index.html#resources-pointHist-get").withRel("profile"));

		  return ResponseEntity.ok(pointHistResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putPointHist(@PathVariable Integer id,
											  @RequestBody @Valid PointHistUpdateDto pointHistUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.pointHistValidator.validate(pointHistUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<PointHist> pointHistOptional = this.pointHistRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (pointHistOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  PointHist existPointHist = pointHistOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  PointHist savePointHist = this.pointHistService.update(pointHistUpdateDto, existPointHist);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PointHistResource pointHistResource = new PointHistResource(savePointHist);
		  pointHistResource.add(new Link("/docs/index.html#resources-pointHist-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(pointHistResource);
	 }
}
