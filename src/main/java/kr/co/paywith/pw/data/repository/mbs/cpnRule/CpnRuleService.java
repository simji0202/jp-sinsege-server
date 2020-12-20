package kr.co.paywith.pw.data.repository.mbs.cpnRule;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CpnRuleService {

    @Autowired
    private CpnRuleRepository cpnRuleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 정보 등록
     */
    @Transactional
    public CpnRule create(CpnRule cpnRule) {

        // 데이터베이스 값 갱신
        CpnRule newCpnRule = this.cpnRuleRepository.save(cpnRule);

        return newCpnRule;
    }


    /**
     * 정보 갱신
     */
    @Transactional
    public CpnRule update(CpnRuleUpdateDto cpnRuleUpdateDto, CpnRule existCpnRule) {

        // 입력값 대입
        this.modelMapper.map(cpnRuleUpdateDto, existCpnRule);

        // 데이터베이스 값 갱신
        this.cpnRuleRepository.save(existCpnRule);

        return existCpnRule;
    }

}
