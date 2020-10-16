package kr.co.paywith.pw.data.repository.schedule;


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
@RequestMapping(value = "/api/schedule", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@Api(value = "scheduleController", description = "schedule", basePath = "/api/schedule")
public class ScheduleController {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private ScheduleValidator scheduleValidator;

	@Autowired
	private ModelMapper modelMapper;


	@Autowired
	private ScheduleService scheduleService;



	@PostMapping
	private ResponseEntity insertSchedule (@RequestBody @Valid ScheduleDto scheduleDto,
												Errors errors,
												@CurrentUser Admin currentUser){
		// 입력 값 체크
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		// 논리적 오류 (제약조건) 체크
		scheduleValidator.validate(scheduleDto, errors);
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

	//	Schedule newSchedule = this.scheduleService.createSchedule(scheduleDto, currentUser);


		Schedule schedule = modelMapper.map(scheduleDto, Schedule.class);
		// customer.update();
		if ( currentUser != null ) {
				schedule.setCreateBy(currentUser.getAdminNm());
				schedule.setUpdateBy(currentUser.getAdminNm());
		}
		Schedule newSchedule = this.scheduleRepository.save(schedule);

		ControllerLinkBuilder selfLinkBuilder = linkTo(ScheduleController.class).slash(newSchedule.getId());
		URI createdUri = selfLinkBuilder.toUri();

		ScheduleResource scheduleResource = new ScheduleResource(newSchedule);
		// Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		scheduleResource.add(linkTo(ScheduleController.class).withRel("query-schedule"));
		scheduleResource.add(selfLinkBuilder.withRel("update-schedule"));
		scheduleResource.add(new Link("/docs/index.html#resources-schedule-create").withRel("profile"));

		return ResponseEntity.created(createdUri).body(scheduleResource);

	}


	@GetMapping
	public ResponseEntity getSchedules(SearchForm searchForm,
									   Pageable pageable,
									   PagedResourcesAssembler<Schedule> assembler
			, @AuthenticationPrincipal AdminAdapter user
			, @CurrentUser Admin currentUser) {

		// 인증상태의 유저 정보 확인
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User princpal = (User) authentication.getPrincipal();


		QSchedule qSchedule = QSchedule.schedule;

		BooleanBuilder  booleanBuilder = new BooleanBuilder();

		// 검색조건 파트너
		if (searchForm.getPartnersId() != null && searchForm.getPartnersId() > 0) {
			booleanBuilder.and(qSchedule.partners.id.eq(searchForm.getPartnersId()));
		}

		// 회사 아이디
		if ( searchForm.getCompanyId() != null && searchForm.getCompanyId() > 0) {
			booleanBuilder.and(qSchedule.partners.company.id.eq(searchForm.getCompanyId()));
		}

		if (searchForm.getScheduleName() != null ) {
			booleanBuilder.and(qSchedule.scheduleName.containsIgnoreCase(searchForm.getScheduleName()));
		}

		Page<Schedule> page = this.scheduleRepository.findAll(booleanBuilder, pageable);
		var pagedResources = assembler.toResource(page, e -> new ScheduleResource(e));
		pagedResources.add(new Link("/docs/index.html#resources-schedules-list").withRel("profile"));
		if (user != null) {
			pagedResources.add(linkTo(ScheduleController.class).withRel("create-schedule"));
		}
		return ResponseEntity.ok(pagedResources);
	}


	@GetMapping("/{id}")
	public ResponseEntity getSchedule(@PathVariable Integer id,
										  @CurrentUser Admin currentUser){

		Optional<Schedule> scheduleOptional = this.scheduleRepository.findById(id);

		// 일정표 정보 체크
		if ( scheduleOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Schedule schedule =  scheduleOptional.get();

		// Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		ScheduleResource scheduleResource = new ScheduleResource(schedule);
		scheduleResource.add(new Link("/docs/index.html#resources-schedule-get").withRel("profile"));

		return ResponseEntity.ok(scheduleResource);
	}



	@PutMapping("/{id}")
	public ResponseEntity putSchedule ( @PathVariable Integer id,
											@RequestBody @Valid ScheduleDto eventDto,
											Errors errors,
											@CurrentUser Admin currentUser) {
		// 입력체크
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		// 논리적 오류 (제약조건) 체크
		this.scheduleValidator.validate(eventDto, errors);
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		Optional<Schedule> scheduleOptional = this.scheduleRepository.findById(id);

		if (scheduleOptional.isEmpty()) {
			// 404 Error return
			return ResponseEntity.notFound().build();
		}

		Schedule existSchedule = scheduleOptional.get();
//			if (!existingEvent.getManager().equals(currentUser)) {
//				return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//			}

		this.modelMapper.map(eventDto, existSchedule);

		// 변경자 정보 저장
		if ( currentUser != null ) {
			existSchedule.setUpdateBy(currentUser.getAdminNm());
		}
		// 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
		// 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
		Schedule saveSchedule = this.scheduleRepository.save(existSchedule);

		// Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
		ScheduleResource scheduleResource = new ScheduleResource(saveSchedule);
		scheduleResource.add(new Link("/docs/index.html#resources-schedule-update").withRel("profile"));

		// 정상적 처리
		return ResponseEntity.ok(scheduleResource);

	}

	private ResponseEntity badRequest(Errors errors) {
		return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	}}