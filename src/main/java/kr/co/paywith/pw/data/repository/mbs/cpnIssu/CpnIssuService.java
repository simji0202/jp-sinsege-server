package kr.co.paywith.pw.data.repository.mbs.cpnIssu;


import java.time.ZonedDateTime;
import javax.transaction.Transactional;

import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.enumeration.CpnIssuRuleType;
import kr.co.paywith.pw.data.repository.enumeration.StampHistType;
import kr.co.paywith.pw.data.repository.mbs.brand.BrandSetting;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnRepository;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnService;
import kr.co.paywith.pw.data.repository.mbs.stampHist.StampHist;
import kr.co.paywith.pw.data.repository.mbs.stampHist.StampHistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CpnIssuService {

    @Value("${cpn-stamp-cnt}")
    private Integer cpnStampCnt = 10;

    @Autowired
    private CpnIssuRepository cpnIssuRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private CpnService cpnService;

    @Autowired
    private CpnRepository cpnRepository;

    @Autowired
    private StampHistService stampHistService;

    /**
     * 정보 등록.
     *
     * 스탬프 쿠폰을 발급한다면 이 메소드 수행 전 회원 정보 변경이 선행되어야 한다.
     */
    @Transactional
    public CpnIssu create(CpnIssu cpnIssu, Account account) {

        // 데이터베이스 값 갱신
        CpnIssu newCpnIssu = this.cpnIssuRepository.save(cpnIssu);

        // 쿠폰 번호 생성
        for (Cpn cpn : newCpnIssu.getCpnList()) {
        //    cpn.setCpnMaster(cpnIssu.getCpnMaster());

            // 부모 클라스 설정
            cpn.setCpnIssu(newCpnIssu);
            cpn.setCreateBy(cpnIssu.getCreateBy());

            // cpn.cpnIssu를 사용해서 cpnNo 설정
            cpn.setCpnNo(cpnService.getCpnNo(cpn));

            // 발행 주체 설정
            if ( account != null ) {
                cpn.setCreateBy(account.getAccountId());
                cpn.setUpdateBy(account.getAccountId());
            }
            cpnRepository.save(cpn);
        }

        newCpnIssu.setIssuCnt(newCpnIssu.getCpnList().size());
        if (cpnIssu.getCpnIssuRule() != null &&
            cpnIssu.getCpnIssuRule().getCpnIssuRuleType().equals(CpnIssuRuleType.STAMP)) {

            BrandSetting brandSetting = cpnIssu.getCpnIssuRule().getCpnMaster().getBrand().getBrandSetting();
            // 스탬프 달성한 순간 발급하는 쿠폰이므로 stampHist도 생성해야 한다.
            for (Cpn cpn : newCpnIssu.getCpnList()) {
                StampHist stampHist = new StampHist();
                stampHist.setCnt(1 * cpnStampCnt); // 정책의 달성해야 할 스탬프 개수 곱
                stampHist.setCpnIssu(cpnIssu);
                stampHist.setStampHistType(StampHistType.CPN);
                stampHist.setUserInfo(cpn.getUserInfo());
                stampHistService.create(stampHist);
            }
        }

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
