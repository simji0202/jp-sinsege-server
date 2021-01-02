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

        // 입력값 대입
        this.modelMapper.map(brandUpdateDto, existBrand);

        // 데이터베이스 값 갱신
        this.brandRepository.save(existBrand);

        return existBrand;
    }

    /**
     * 브랜드 접근 권한이 있는 다른 브랜드 인지 확인. brandCd를 사용하여 비교한다
     *
     * @param currentBrand 현재 Account, 클라이언트 등의 브랜드
     * @param targetBrand 확인하려고 하는 브랜드(조회, 등록 등을 하는 브랜드)
     * @return 권한이 있으면 true
     */
    public boolean hasAuthorization(Brand currentBrand, Brand targetBrand) {
        if (currentBrand == null) {
            // null 인 경우 브랜드 관리하지 않은 최상위 관리자 이므로 true
            return true;
        }
        return targetBrand.getBrandCd().startsWith(currentBrand.getBrandCd());
    }
}
