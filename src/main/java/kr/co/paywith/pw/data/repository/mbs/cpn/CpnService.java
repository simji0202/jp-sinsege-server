package kr.co.paywith.pw.data.repository.mbs.cpn;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CpnService {

    @Autowired
    private CpnRepository cpnRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 정보 등록
     */
    @Transactional
    public Cpn create(Cpn cpn) {

        // 데이터베이스 값 갱신
        Cpn newCpn = this.cpnRepository.save(cpn);

        return newCpn;
    }


    /**
     * 정보 갱신
     */
    @Transactional
    public Cpn update(CpnUpdateDto cpnUpdateDto, Cpn existCpn) {

        // 입력값 대입
        this.modelMapper.map(cpnUpdateDto, existCpn);

        // 데이터베이스 값 갱신
        this.cpnRepository.save(existCpn);

        return existCpn;
    }

}
