package kr.co.paywith.pw.data.repository.busCompany;


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
@RequestMapping(value = "/api/busCompany", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@Api(value = "busCompanyController", description = "busCompany", basePath = "/api/busCompany")
public class BusCompanyController {

	@Autowired
	private BusCompanyRepository busCompanyRepository;

	@Autowired
	private BusCompanyValidator busCompanyValidator;

	@Autowired
	private ModelMapper modelMapper;


	@Autowired
	private BusCompanyService busCompanyService;



	@PostMapping
	private ResponseEntity insertBusCompany (@RequestBody @Valid BusCompanyDto busCompanyDto,
												Errors errors,
												@CurrentUser Admin currentUser){
		// 입력 값 체크
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		// 논리적 오류 (제약조건) 체크
		busCompanyValidator.validate(busCompanyDto, errors);
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

	//	BusCompany newBusCompany = this.busCompanyService.createBusCompany(busCompanyDto, currentUser);


		BusCompany busCompany = modelMapper.map(busCompanyDto, BusCompany.class);
		// customer.update();
		if ( currentUser != null ) {
				busCompany.setCreateBy(currentUser.getAdminNm());
				busCompany.setUpdateBy(currentUser.getAdminNm());
		}
		BusCompany newBusCompany = this.busCompanyRepository.save(busCompany);

		ControllerLinkBuilder selfLinkBuilder = linkTo(BusCompanyController.class).slash(newBusCompany.getId());
		URI createdUri = selfLinkBuilder.toUri();

		BusCompanyResource busCompanyResource = new BusCompanyResource(newBusCompany);
		// Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		busCompanyResource.add(linkTo(BusCompanyController.class).withRel("query-busCompany"));
		busCompanyResource.add(selfLinkBuilder.withRel("update-busCompany"));
		busCompanyResource.add(new Link("/docs/index.html#resources-busCompany-create").withRel("profile"));

		return ResponseEntity.created(createdUri).body(busCompanyResource);

	}


	@GetMapping
	public ResponseEntity getBusCompanys(SearchForm searchForm,
										 Pageable pageable,
										 PagedResourcesAssembler<BusCompany> assembler
			, @AuthenticationPrincipal AdminAdapter user
			, @CurrentUser Admin currentUser) {

		// 인증상태의 유저 정보 확인
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User princpal = (User) authentication.getPrincipal();


		//QBusCompany qBusCompany = QBusCompany.busCompany;

		BooleanBuilder  booleanBuilder = new BooleanBuilder();

		// 검색조건 입금은행명
		//if (searchForm.getNumber() > 0) {
			//booleanBuilder.and(qBusCompany.number.eq(searchForm.getNumber()));
		//}


		Page<BusCompany> page = this.busCompanyRepository.findAll(booleanBuilder, pageable);
		var pagedResources = assembler.toResource(page, e -> new BusCompanyResource(e));
		pagedResources.add(new Link("/docs/index.html#resources-busCompanys-list").withRel("profile"));
		if (user != null) {
			pagedResources.add(linkTo(BusCompanyController.class).withRel("create-busCompany"));
		}
		return ResponseEntity.ok(pagedResources);
	}


	@GetMapping("/{id}")
	public ResponseEntity getBusCompany(@PathVariable Integer id,
										  @CurrentUser Admin currentUser
										  ){

		Optional<BusCompany> busCompanyOptional = this.busCompanyRepository.findById(id);

		// 버스회사정보 정보 체크
		if ( busCompanyOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		BusCompany busCompany =  busCompanyOptional.get();

		// Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		BusCompanyResource busCompanyResource = new BusCompanyResource(busCompany);
		busCompanyResource.add(new Link("/docs/index.html#resources-busCompany-get").withRel("profile"));

		return ResponseEntity.ok(busCompanyResource);
	}



	@PutMapping("/{id}")
	public ResponseEntity putBusCompany ( @PathVariable Integer id,
											@RequestBody @Valid BusCompanyDto eventDto,
											Errors errors,
											@CurrentUser Admin currentUser) {
		// 입력체크
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		// 논리적 오류 (제약조건) 체크
		this.busCompanyValidator.validate(eventDto, errors);
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		Optional<BusCompany> busCompanyOptional = this.busCompanyRepository.findById(id);

		if (busCompanyOptional.isEmpty()) {
			// 404 Error return
			return ResponseEntity.notFound().build();
		}

		BusCompany existBusCompany = busCompanyOptional.get();
//			if (!existingEvent.getManager().equals(currentUser)) {
//				return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//			}

		this.modelMapper.map(eventDto, existBusCompany);

		// 변경자 정보 저장
		if ( currentUser != null ) {
			existBusCompany.setUpdateBy(currentUser.getAdminNm());
		}
		// 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		// 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		BusCompany saveBusCompany = this.busCompanyRepository.save(existBusCompany);

		// Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		BusCompanyResource busCompanyResource = new BusCompanyResource(saveBusCompany);
		busCompanyResource.add(new Link("/docs/index.html#resources-busCompany-update").withRel("profile"));

		// 정상적 처리
		return ResponseEntity.ok(busCompanyResource);

	}

	private ResponseEntity badRequest(Errors errors) {
		return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	}}