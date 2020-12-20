package kr.co.paywith.pw.data.repository.mbs.billingChrg;


import kr.co.paywith.pw.data.repository.mbs.billingChrg.BillingChrg;
import kr.co.paywith.pw.data.repository.mbs.billingChrg.BillingChrgRepository;
import kr.co.paywith.pw.data.repository.mbs.billingChrg.BillingChrgUpdateDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BillingChrgService {

    @Autowired
    private BillingChrgRepository billingChrgRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 정보 등록
     */
    @Transactional
    public BillingChrg create(BillingChrg billingChrg) {

        // 데이터베이스 값 갱신
        BillingChrg newBillingChrg = this.billingChrgRepository.save(billingChrg);

        return newBillingChrg;
    }


    /**
     * 정보 갱신
     */
    @Transactional
    public BillingChrg update(BillingChrgUpdateDto billingChrgUpdateDto, BillingChrg existBillingChrg) {

        // 입력값 대입
        this.modelMapper.map(billingChrgUpdateDto, existBillingChrg);

        // 데이터베이스 값 갱신
        this.billingChrgRepository.save(existBillingChrg);

        return existBillingChrg;
    }

}
