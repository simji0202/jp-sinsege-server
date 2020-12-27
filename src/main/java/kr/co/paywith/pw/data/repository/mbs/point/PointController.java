package kr.co.paywith.pw.data.repository.mbs.point;

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
@RequestMapping(value = "/api/point")
@Api(value = "PointController", description = "쿠폰 API", basePath = "/api/point")
public class PointController extends CommonController {

	 @Autowired
	 PointRepository pointRepository;

	 @Autowired
	 ModelMapper modelMapper;

	 @Autowired
	 PointValidator pointValidator;

	 @Autowired
	 PointService pointService;

	 /**
	  * 정보 등록
	  */
	 @PostMapping
	 public ResponseEntity createPoint(
				@RequestBody @Valid PointDto pointDto,
				Errors errors,
				@CurrentUser Account currentUser) {
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값 체크
		  pointValidator.validate(pointDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }


		  // 입력값을 브랜드 객채에 대입
		  Point point = modelMapper.map(pointDto, Point.class);

		  // 레코드 등록
		  Point newPoint = pointService.create(point);

		  ControllerLinkBuilder selfLinkBuilder = linkTo(PointController.class).slash(newPoint.getId());

		  URI createdUri = selfLinkBuilder.toUri();
		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PointResource pointResource = new PointResource(newPoint);
		  pointResource.add(linkTo(PointController.class).withRel("query-point"));
		  pointResource.add(selfLinkBuilder.withRel("update-point"));
		  pointResource.add(new Link("/docs/index.html#resources-point-create").withRel("profile"));

		  return ResponseEntity.created(createdUri).body(pointResource);
	 }


	 /**
	  * 정보취득 (조건별 page )
	  */
	 @GetMapping
	 public ResponseEntity getPoints(SearchForm searchForm,
												Pageable pageable,
												PagedResourcesAssembler<Point> assembler
				, @CurrentUser Account currentUser) {

		  // 인증상태의 유저 정보 확인
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User princpal = (User) authentication.getPrincipal();

		  QPoint qPoint = QPoint.point;

		  //
		  BooleanBuilder booleanBuilder = new BooleanBuilder();

		  // 검색조건 아이디(키)
		  if (searchForm.getId() != null) {
				booleanBuilder.and(qPoint.id.eq(searchForm.getId()));
		  }


		  Page<Point> page = this.pointRepository.findAll(booleanBuilder, pageable);
		  var pagedResources = assembler.toResource(page, e -> new PointResource(e));
		  pagedResources.add(new Link("/docs/index.html#resources-points-list").withRel("profile"));
		  pagedResources.add(linkTo(PointController.class).withRel("create-point"));
		  return ResponseEntity.ok(pagedResources);
	 }

	 private ResponseEntity badRequest(Errors errors) {
		  return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	 }


	 /**
	  * 정보취득 (1건 )
	  */
	 @GetMapping("/{id}")
	 public ResponseEntity getPoint(@PathVariable Integer id,
											  @CurrentUser Account currentUser) {

		  Optional<Point> pointOptional = this.pointRepository.findById(id);

		  // 고객 정보 체크
		  if (pointOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
		  }

		  Point point = pointOptional.get();

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PointResource pointResource = new PointResource(point);
		  pointResource.add(new Link("/docs/index.html#resources-point-get").withRel("profile"));

		  return ResponseEntity.ok(pointResource);
	 }


	 /**
	  * 정보 변경
	  */
	 @PutMapping("/{id}")
	 public ResponseEntity putPoint(@PathVariable Integer id,
											  @RequestBody @Valid PointUpdateDto pointUpdateDto,
											  Errors errors,
											  @CurrentUser Account currentUser) {
		  // 입력체크
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 논리적 오류 (제약조건) 체크
		  this.pointValidator.validate(pointUpdateDto, errors);
		  if (errors.hasErrors()) {
				return badRequest(errors);
		  }

		  // 기존 테이블에서 관련 정보 취득
		  Optional<Point> pointOptional = this.pointRepository.findById(id);

		  // 기존 정보 유무 체크
		  if (pointOptional.isEmpty()) {
				// 404 Error return
				return ResponseEntity.notFound().build();
		  }

		  // 기존 정보 취득
		  Point existPoint = pointOptional.get();

		  // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		  // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		  Point savePoint = this.pointService.update(pointUpdateDto, existPoint);

		  // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		  PointResource pointResource = new PointResource(savePoint);
		  pointResource.add(new Link("/docs/index.html#resources-point-update").withRel("profile"));

		  // 정상적 처리
		  return ResponseEntity.ok(pointResource);
	 }
}
