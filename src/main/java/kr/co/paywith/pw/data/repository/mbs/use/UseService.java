package kr.co.paywith.pw.data.repository.mbs.use;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UseService {

    @Autowired
    private UseRepository useRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 정보 등록
     */
    @Transactional
    public Use create(Use use) {

        // 데이터베이스 값 갱신
        Use newUse = this.useRepository.save(use);

        return newUse;
    }


    /**
     * 정보 갱신
     */
    @Transactional
    public Use update(UseUpdateDto useUpdateDto, Use existUse) {

        // 입력값 대입
        this.modelMapper.map(useUpdateDto, existUse);

        // 데이터베이스 값 갱신
        this.useRepository.save(existUse);

        return existUse;
    }

}
