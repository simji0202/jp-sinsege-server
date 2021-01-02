package kr.co.paywith.pw.data.repository.mbs.mrhst;


import kr.co.paywith.pw.component.GeoLocationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MrhstService {

    @Autowired
    private MrhstRepository mrhstRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 정보 등록
     */
    @Transactional
    public Mrhst create(Mrhst mrhst) {
        // 사업자 번호에서 - 삭제
        if (mrhst.getCorpNo() != null) {
            mrhst.setCorpNo(mrhst.getCorpNo().replaceAll("-", "").trim());
        }

        mrhst.setCoords(
            GeoLocationUtil.makeGeometry(mrhst.getLat(), mrhst.getLng()).getInteriorPoint());

        // 데이터베이스 값 갱신
        Mrhst newMrhst = this.mrhstRepository.save(mrhst);

        return newMrhst;
    }


    /**
     * 정보 갱신
     */
    @Transactional
    public Mrhst update(MrhstUpdateDto mrhstUpdateDto, Mrhst existMrhst) {

        // 매핑 전 저정된 list 초기화 -> 새 list의 length 만큼만 업데이트 되는 증상 방지
        existMrhst.getImgUrlList().clear();
        existMrhst.getAvailServiceCdList().clear();

        // 입력값 대입
        this.modelMapper.map(mrhstUpdateDto, existMrhst);

        // 사업자 번호에서 - 삭제
        if (existMrhst.getCorpNo() != null) {
            existMrhst.setCorpNo(existMrhst.getCorpNo().replaceAll("-", "").trim());
        }

        existMrhst.setCoords(
            GeoLocationUtil.makeGeometry(existMrhst.getLat(), existMrhst.getLng()).getInteriorPoint());

        // 데이터베이스 값 갱신
        this.mrhstRepository.save(existMrhst);

        return existMrhst;
    }


}
