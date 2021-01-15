package kr.co.paywith.pw.data.repository.mbs.cpnIssu;


import java.time.ZonedDateTime;
import java.util.List;
import javax.transaction.Transactional;

import kr.co.paywith.pw.data.repository.account.Account;
import kr.co.paywith.pw.data.repository.admin.CurrentUser;
import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnRepository;
import kr.co.paywith.pw.data.repository.mbs.cpn.CpnService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Autowired
    private CpnRepository cpnRepository;

    /**
     * 정보 등록
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
                cpn.setCreateBy(account.getAccountNm());
                cpn.setUpdateBy(account.getAccountNm());
            }
            cpnRepository.save(cpn);
        }

        newCpnIssu.setIssuCnt(newCpnIssu.getCpnList().size());

//        if (cpnIssu.getStampHist() != null) {
//            // kms: TODO 멤버십 구조 변경 후 생성
////            // 스탬프 달성한 순간 발급하는 쿠폰이므로 stampHist도 생성해야 한다.
////            StampHist stampHist = new StampHist();
////            stampHist.setCnt(cpnIssu.getCpnList() * ); // 정책의 달성해야 할 스탬프 개수 곱
////            stampHist.setCpnIssu(cpnIssu);
////            stampHist.setStampHistTypeCd(StampHistTypeCd.CPN);
////            stampHist.setUserInfo(cpnIssu.getStampHist().getUserInfo());
////            stampHistService.create(stampHist)
//        }
        return newCpnIssu;
    }


    /**
     * 정보 갱신
     */
    @Transactional
    public CpnIssu update(CpnIssuUpdateDto cpnIssuUpdateDto, CpnIssu existCpnIssu) {

        // 입력값 대입
        this.modelMapper.map(cpnIssuUpdateDto, existCpnIssu);

        // 기존 관련 리스트 초기화
        if (existCpnIssu.getCpnList() != null) {
            existCpnIssu.getCpnList().clear();
            existCpnIssu.getCpnList().addAll(cpnIssuUpdateDto.getCpnList());
        }
        // 변경된 리스트 취득
        List<Cpn> cpnList = cpnIssuUpdateDto.getCpnList();

        // 쿠폰 관련상품들 등록
        if (cpnList != null && cpnList.size() > 0) {

            cpnList.forEach( cpn -> {
                cpn.setCpnIssu(existCpnIssu);
                this.cpnRepository.save(cpn);
            });
        }

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
