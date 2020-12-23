package kr.co.paywith.pw.data.repository.mbs.bbs;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BbsService {

    @Autowired
    private BbsRepository bbsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 정보 등록
     */
    @Transactional
    public Bbs create(Bbs bbs) {

        // 데이터베이스 값 갱신
        Bbs newBbs = this.bbsRepository.save(bbs);

        return newBbs;
    }


    /**
     * 정보 갱신
     */
    @Transactional
    public Bbs update(BbsUpdateDto bbsUpdateDto, Bbs existBbs) {

        // 입력값 대입
        this.modelMapper.map(bbsUpdateDto, existBbs);

        // 데이터베이스 값 갱신
        this.bbsRepository.save(existBbs);

        return existBbs;
    }

}
