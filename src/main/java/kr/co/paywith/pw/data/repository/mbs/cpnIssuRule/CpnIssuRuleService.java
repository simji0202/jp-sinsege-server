package kr.co.paywith.pw.data.repository.mbs.cpnIssuRule;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CpnIssuRuleService {

    @Autowired
    private CpnIssuRuleRepository cpnIssuRuleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 정보 등록
     */
    @Transactional
    public CpnIssuRule create(CpnIssuRule cpnIssuRule) {

        // 데이터베이스 값 갱신
        CpnIssuRule newCpnIssuRule = this.cpnIssuRuleRepository.save(cpnIssuRule);

        return newCpnIssuRule;
    }


    /**
     * 정보 갱신
     */
    @Transactional
    public CpnIssuRule update(CpnIssuRuleUpdateDto cpnIssuRuleUpdateDto, CpnIssuRule existCpnIssuRule) {

        // 입력값 대입
        this.modelMapper.map(cpnIssuRuleUpdateDto, existCpnIssuRule);

        // 데이터베이스 값 갱신
        this.cpnIssuRuleRepository.save(existCpnIssuRule);

        return existCpnIssuRule;
    }

}
