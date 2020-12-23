package kr.co.paywith.pw.data.repository.mbs.billing;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 정보 등록
     */
    @Transactional
    public Billing create(Billing billing) {

        // 데이터베이스 값 갱신
        Billing newBilling = this.billingRepository.save(billing);

        return newBilling;
    }


    /**
     * 정보 갱신
     */
    @Transactional
    public Billing update(BillingUpdateDto billingUpdateDto, Billing existBilling) {

        // 입력값 대입
        this.modelMapper.map(billingUpdateDto, existBilling);

        // 데이터베이스 값 갱신
        this.billingRepository.save(existBilling);

        return existBilling;
    }

}
