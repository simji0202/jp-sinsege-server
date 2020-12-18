package kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl;


import kr.co.paywith.pw.data.repository.admin.Admin;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MrhstTrmnlService {

    @Autowired
    private MrhstTrmnlRepository mrhstTrmnlRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 정보 등록
     */
    @Transactional
    public MrhstTrmnl create(MrhstTrmnl mrhstTrmnl) {

        // 데이터베이스 값 갱신
        mrhstTrmnl.setUserPw(this.passwordEncoder.encode(mrhstTrmnl.getUserPw()));

        MrhstTrmnl newMrhstTrmnl = this.mrhstTrmnlRepository.save(mrhstTrmnl);

        return newMrhstTrmnl;
    }


    /**
     * 정보 갱신
     */
    @Transactional
    public MrhstTrmnl update(MrhstTrmnlUpdateDto mrhstTrmnlUpdateDto, MrhstTrmnl existMrhstTrmnl) {

        // 입력값 대입
        this.modelMapper.map(mrhstTrmnlUpdateDto, existMrhstTrmnl);

        // 데이터베이스 값 갱신
        this.mrhstTrmnlRepository.save(existMrhstTrmnl);

        return existMrhstTrmnl;
    }


    /**
     * 패스워드 변경
     */
    @Transactional
    public MrhstTrmnl updatePw(MrhstTrmnl mrhstTrmnl) {

        mrhstTrmnl.setUserPw(this.passwordEncoder.encode(mrhstTrmnl.getUserPw()));
        return this.mrhstTrmnlRepository.save(mrhstTrmnl);
    }

}

