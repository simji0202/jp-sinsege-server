package kr.co.paywith.pw.data.repository.requestsBus;


import com.querydsl.core.BooleanBuilder;
import kr.co.paywith.pw.common.ErrorsResource;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import io.swagger.annotations.Api;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/requestBus", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@Api(value = "requestBusController", description = "requestBus", basePath = "/api/requestBus")
public class RequestBusController {

	@Autowired
	private RequestBusRepository requestBusRepository;

	@Autowired
	private RequestBusValidator requestBusValidator;

	@Autowired
	private ModelMapper modelMapper;


	@Autowired
	private RequestBusService requestBusService;



	@PostMapping
	private ResponseEntity insertRequestBus (@RequestBody @Valid RequestBusDto requestBusDto,
												Errors errors,
												@CurrentUser Admin currentUser){
		// 입력 값 체크
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		// 논리적 오류 (제약조건) 체크
		requestBusValidator.validate(requestBusDto, errors);
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

	//	RequestBus newRequestBus = this.requestBusService.createRequestBus(requestBusDto, currentUser);


		RequestBus requestBus = modelMapper.map(requestBusDto, RequestBus.class);

		RequestBus newRequestBus = this.requestBusRepository.save(requestBus);

		ControllerLinkBuilder selfLinkBuilder = linkTo(RequestBusController.class).slash(newRequestBus.getId());
		URI createdUri = selfLinkBuilder.toUri();

		RequestBusResource requestBusResource = new RequestBusResource(newRequestBus);
		// Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		requestBusResource.add(linkTo(RequestBusController.class).withRel("query-requestBus"));
		requestBusResource.add(selfLinkBuilder.withRel("update-requestBus"));
		requestBusResource.add(new Link("/docs/index.html#resources-requestBus-create").withRel("profile"));

		return ResponseEntity.created(createdUri).body(requestBusResource);

	}


	@GetMapping
	public ResponseEntity getRequestBuss(SearchForm searchForm,
										 Pageable pageable,
										 PagedResourcesAssembler<RequestBus> assembler
			, @AuthenticationPrincipal AdminAdapter user
			, @CurrentUser Admin currentUser) {

		// 인증상태의 유저 정보 확인
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User princpal = (User) authentication.getPrincipal();


		//QRequestBus qRequestBus = QRequestBus.requestBus;

		BooleanBuilder  booleanBuilder = new BooleanBuilder();

		// 검색조건 입금은행명
		//if (searchForm.getNumber() > 0) {
			//booleanBuilder.and(qRequestBus.number.eq(searchForm.getNumber()));
		//}


		Page<RequestBus> page = this.requestBusRepository.findAll(booleanBuilder, pageable);
		var pagedResources = assembler.toResource(page, e -> new RequestBusResource(e));
		pagedResources.add(new Link("/docs/index.html#resources-requestBuss-list").withRel("profile"));
		if (user != null) {
			pagedResources.add(linkTo(RequestBusController.class).withRel("create-requestBus"));
		}
		return ResponseEntity.ok(pagedResources);
	}


	@GetMapping ("/agent")
	public ResponseEntity getRequestBusList(SearchForm searchForm,
										 Pageable pageable,
										 PagedResourcesAssembler<RequestBus> assembler
			, @AuthenticationPrincipal AdminAdapter user
			, @CurrentUser Admin currentUser) {

		// 인증상태의 유저 정보 확인
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User princpal = (User) authentication.getPrincipal();


		QRequestBus qRequestBus = QRequestBus.requestBus;

		BooleanBuilder booleanBuilder = new BooleanBuilder();

		// 요청 아이디
		if (searchForm.getRequestsId() != null && searchForm.getRequestsId() > 0) {
			booleanBuilder.and(qRequestBus.requests.id.eq(searchForm.getRequestsId()));
		}

		// 회사 아이디
		if (searchForm.getAgentId() != null && searchForm.getAgentId() > 0) {
			booleanBuilder.and(qRequestBus.agent.id.eq(searchForm.getAgentId()));
		}


		Page<RequestBus> page = this.requestBusRepository.findAll(booleanBuilder, pageable);
		var pagedResources = assembler.toResource(page, e -> new RequestBusResource(e));
		pagedResources.add(new Link("/docs/index.html#resources-requestBuss-list").withRel("profile"));
		if (user != null) {
			pagedResources.add(linkTo(RequestBusController.class).withRel("create-requestBus"));
		}
		return ResponseEntity.ok(pagedResources);
	}




	@GetMapping("/{id}")
	public ResponseEntity getRequestBus(@PathVariable Integer id,
										  @CurrentUser Admin currentUser
										  ){

		Optional<RequestBus> requestBusOptional = this.requestBusRepository.findById(id);

		// 예약_버스상세 정보 체크
		if ( requestBusOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		RequestBus requestBus =  requestBusOptional.get();

		// Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		RequestBusResource requestBusResource = new RequestBusResource(requestBus);
		requestBusResource.add(new Link("/docs/index.html#resources-requestBus-get").withRel("profile"));

		return ResponseEntity.ok(requestBusResource);
	}



	@PutMapping("/{id}")
	public ResponseEntity putRequestBus ( @PathVariable Integer id,
											@RequestBody @Valid RequestBusDto eventDto,
											Errors errors,
											@CurrentUser Admin currentUser) {
		// 입력체크
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		// 논리적 오류 (제약조건) 체크
		this.requestBusValidator.validate(eventDto, errors);
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		Optional<RequestBus> requestBusOptional = this.requestBusRepository.findById(id);

		if (requestBusOptional.isEmpty()) {
			// 404 Error return
			return ResponseEntity.notFound().build();
		}

		RequestBus existRequestBus = requestBusOptional.get();
//			if (!existingEvent.getManager().equals(currentUser)) {
//				return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//			}

		this.modelMapper.map(eventDto, existRequestBus);

		// 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		// 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		RequestBus saveRequestBus = this.requestBusRepository.save(existRequestBus);

		// Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		RequestBusResource requestBusResource = new RequestBusResource(saveRequestBus);
		requestBusResource.add(new Link("/docs/index.html#resources-requestBus-update").withRel("profile"));

		// 정상적 처리
		return ResponseEntity.ok(requestBusResource);

	}

	private ResponseEntity badRequest(Errors errors) {
		return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	}}