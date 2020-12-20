package kr.co.paywith.pw.data.repository.mbs.cpnIssu;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CpnIssuService {

    @Autowired
    private CpnIssuRepository cpnIssuRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 정보 등록
     */
    @Transactional
    public CpnIssu create(CpnIssu cpnIssu) {

        // 데이터베이스 값 갱신
        CpnIssu newCpnIssu = this.cpnIssuRepository.save(cpnIssu);

        return newCpnIssu;
    }


    /**
     * 정보 갱신
     */
    @Transactional
    public CpnIssu update(CpnIssuUpdateDto cpnIssuUpdateDto, CpnIssu existCpnIssu) {

        // 입력값 대입
        this.modelMapper.map(cpnIssuUpdateDto, existCpnIssu);

        // 데이터베이스 값 갱신
        this.cpnIssuRepository.save(existCpnIssu);

        return existCpnIssu;
    }

}
