package kr.co.paywith.pw.data.repository.mbs.useReq;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UseReqService {

    @Autowired
    private UseReqRepository useReqRepository;


    @Autowired
    private ModelMapper modelMapper;


    /**
     *
     */
    @Transactional
    public UseReq create(UseReq useReq) {

        // 데이터베이스 값 갱신
        UseReq newUseReq = this.useReqRepository.save(useReq);

        return newUseReq;
    }


    /**
     * 정보 갱신
     *
     * @param useReqUpdateDto
     * @param existUseReq
     * @return
     */
    @Transactional
    public UseReq update(UseReqUpdateDto useReqUpdateDto, UseReq existUseReq) {

        // 입력값 대입
        this.modelMapper.map(useReqUpdateDto, existUseReq);

        // 데이터베이스 값 갱신
        this.useReqRepository.save(existUseReq);

        return existUseReq;
    }

}
