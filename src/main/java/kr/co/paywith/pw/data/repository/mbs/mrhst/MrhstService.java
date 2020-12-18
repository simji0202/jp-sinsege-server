package kr.co.paywith.pw.data.repository.mbs.mrhst;


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

        // 데이터베이스 값 갱신
        Mrhst newMrhst = this.mrhstRepository.save(mrhst);

        return newMrhst;
    }


    /**
     * 정보 갱신
     */
    @Transactional
    public Mrhst update(MrhstUpdateDto mrhstUpdateDto, Mrhst existMrhst) {

        // 입력값 대입
        this.modelMapper.map(mrhstUpdateDto, existMrhst);

        // 데이터베이스 값 갱신
        this.mrhstRepository.save(existMrhst);

        return existMrhst;
    }

}
