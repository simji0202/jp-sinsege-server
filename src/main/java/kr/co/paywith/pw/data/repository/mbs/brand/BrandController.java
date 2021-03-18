package kr.co.paywith.pw.data.repository.mbs.brand;

import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;

import kr.co.paywith.pw.common.ErrorsResource;
import kr.co.paywith.pw.data.repository.SearchForm;
import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.account.CurrentUser;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/brand")
@Api(value = "BrandController", description = "브랜드 API", basePath = "/api/brand")
public class  BrandController extends  CommonController {

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BrandValidator brandValidator;

    @Autowired
    BrandService brandService;

    @PostMapping
    public ResponseEntity createBrand(
            @RequestBody @Valid BrandDto brandDto,
            Errors errors,
            @CurrentUser Account currentUser) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 입력값 체크
        brandValidator.validate(brandDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }


        // 입력값을 브랜드 객채에 대입
        Brand brand = modelMapper.map(brandDto, Brand.class);

        // 현재 로그인 유저 설정
        if (currentUser != null) {
            brand.setCreateBy(currentUser.getAccountId());
            brand.setUpdateBy(currentUser.getAccountId());
        }

        // 레코드 등록
        Brand newBrand = brandRepository.save(brand);

        ControllerLinkBuilder selfLinkBuilder = linkTo(BrandController.class).slash(newBrand.getId());

        URI createdUri = selfLinkBuilder.toUri();
        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        BrandResource brandResource = new BrandResource(newBrand);
        brandResource.add(linkTo(BrandController.class).withRel("query-brand"));
        brandResource.add(selfLinkBuilder.withRel("update-brand"));
        brandResource.add(new Link("/docs/index.html#resources-brand-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(brandResource);
    }


    @GetMapping
    public ResponseEntity getBrands(SearchForm searchForm,
                                       Pageable pageable,
                                       PagedResourcesAssembler<Brand> assembler
            , @CurrentUser Account currentUser) {

        // 인증상태의 유저 정보 확인
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User princpal = (User) authentication.getPrincipal();

        QBrand qBrand = QBrand.brand;

        //
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 검색조건 아이디(키)
        if (searchForm.getId() != null) {
            booleanBuilder.and(qBrand.id.eq(searchForm.getId()));
        }



        Page<Brand> page = this.brandRepository.findAll(booleanBuilder, pageable);
        var pagedResources = assembler.toResource(page, e -> new BrandResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-brands-list").withRel("profile"));
        pagedResources.add(linkTo(BrandController.class).withRel("create-brand"));
        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }


    /**
     *
     */
    @GetMapping("/{id}")
    public ResponseEntity getBrand(@PathVariable Integer id,
                                      @CurrentUser Account currentUser) {

        Optional<Brand> brandOptional = this.brandRepository.findById(id);

        // 고객 정보 체크
        if (brandOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Brand brand = brandOptional.get();

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        BrandResource brandResource = new BrandResource(brand);
        brandResource.add(new Link("/docs/index.html#resources-brand-get").withRel("profile"));

        return ResponseEntity.ok(brandResource);
    }


    /**
     *
     */
    @PutMapping("/{id}")
    public ResponseEntity putBrand(@PathVariable Integer id,
                                      @RequestBody @Valid BrandUpdateDto brandUpdateDto,
                                      Errors errors,
                                      @CurrentUser Account currentUser) {
        // 입력체크
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

      // 기존 테이블에서 관련 정보 취득
      Optional<Brand> brandOptional = this.brandRepository.findById(id);

      // 기존 정보 유무 체크
      if (brandOptional.isEmpty()) {
        // 404 Error return
        return ResponseEntity.notFound().build();
      }

      // 기존 정보 취득
      Brand existBrand = brandOptional.get();

      // 논리적 오류 (제약조건) 체크
        this.brandValidator.validate(brandUpdateDto, existBrand, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        if (currentUser != null) {
            // 변경자 정보 저장
            existBrand.setUpdateBy(currentUser.getAccountId());
        }

        // 변경사항이 자동으로 적용되지 않기 때문에 수동으로 저장
        // 자동 적용은 service class {  @Transactional Method  } 형식으로 구현해서 Transactional안에서 처리할 필요가 있음
        Brand saveBrand = this.brandService.update(brandUpdateDto, existBrand);

        // Hateoas 관련 클래스를 이용하여 필요한 링크 정보 추가
        BrandResource brandResource = new BrandResource(saveBrand);
        brandResource.add(new Link("/docs/index.html#resources-brand-update").withRel("profile"));

        // 정상적 처리
        return ResponseEntity.ok(brandResource);
    }


}



