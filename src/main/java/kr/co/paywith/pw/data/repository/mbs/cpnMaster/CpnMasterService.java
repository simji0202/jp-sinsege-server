package kr.co.paywith.pw.data.repository.mbs.cpnMaster;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CpnMasterService {

    @Autowired
    private CpnMasterRepository cpnMasterRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 정보 등록
     */
    @Transactional
    public CpnMaster create(CpnMaster cpnMaster) {

        // 데이터베이스 값 갱신
        CpnMaster newCpnMaster = this.cpnMasterRepository.save(cpnMaster);

        // TODO cpnGoodsList 갱신
        return newCpnMaster;
    }


    /**
     * 정보 갱신
     */
    @Transactional
    public CpnMaster update(CpnMasterUpdateDto cpnMasterUpdateDto, CpnMaster existCpnMaster) {

        // 입력값 대입
        this.modelMapper.map(cpnMasterUpdateDto, existCpnMaster);

        // 데이터베이스 값 갱신
        this.cpnMasterRepository.save(existCpnMaster);

        // TODO cpnGoodsList 갱신? 잘 되는지 확인 후 안되면 list clear

        return existCpnMaster;
    }

}
