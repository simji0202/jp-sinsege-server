package kr.co.paywith.pw.data.repository.mbs.brand;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;


    @Autowired
    private ModelMapper modelMapper;


    /**
     * 브랜드 정보 갱신
     */
    @Transactional
    public Brand update(BrandUpdateDto brandUpdateDto, Brand existBrand) {

        // 기존 List 항목 초기 설정
        existBrand.getAvailBrandFnCdList().clear();
        existBrand.getAvailServiceCdList().clear();
        // kms: 삭제가능. ROLE 로 대체
//        existBrand.getMenuItemCdBMstList().clear();
//        existBrand.getMenuItemCdSMstList().clear();
//        existBrand.getEditableMenuItemCdBMstList().clear();
//        existBrand.getEditableMenuItemCdSMstList().clear();

        // 입력값 대입
        this.modelMapper.map(brandUpdateDto, existBrand);

        // 데이터베이스 값 갱신
        this.brandRepository.save(existBrand);

        return existBrand;
    }

}
