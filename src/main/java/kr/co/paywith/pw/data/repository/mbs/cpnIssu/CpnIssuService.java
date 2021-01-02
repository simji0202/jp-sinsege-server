package kr.co.paywith.pw.data.repository.mbs.cpnIssu;


import java.time.ZonedDateTime;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnService;
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

    @Autowired
    private CpnService cpnService;

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

    /**
     * 쿠폰 발급 취소.
     * cpnList의 쿠폰이 모두 사용가능한 상태여야 가능하며, cpn 들을 모두 무효처리한다
     */
    @Transactional
    public void delete(CpnIssu cpnIssu) {
        // 쿠폰 무효처리. 이전 validator에서 사용가능 쿠폰인지 모두 확인했어야 함
        for (Cpn cpn : cpnIssu.getCpnList()) {
            cpnService.delete(cpn);
        }
        cpnIssu.setValidEndDttm(ZonedDateTime.now());
    }
}
