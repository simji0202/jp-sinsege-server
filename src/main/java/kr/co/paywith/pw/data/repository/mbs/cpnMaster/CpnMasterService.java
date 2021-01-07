package kr.co.paywith.pw.data.repository.mbs.cpnMaster;


import kr.co.paywith.pw.data.repository.mbs.cpnMasterGoods.CpnMasterGoods;
import kr.co.paywith.pw.data.repository.mbs.cpnMasterGoods.CpnMasterGoodsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CpnMasterService {

    @Autowired
    private CpnMasterRepository cpnMasterRepository;

    @Autowired
    private CpnMasterGoodsRepository cpnMasterGoodsRepository;


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

        // 쿠폰 관련상품들 취득
        List<CpnMasterGoods> cpnMasterGoodsList = cpnMaster.getCpnMasterGoodsList();

        // 쿠폰 관련상품들 등록
        if (cpnMasterGoodsList != null && cpnMasterGoodsList.size() > 0 ) {

            cpnMasterGoodsList.forEach(cpnMasterGoods -> {
                cpnMasterGoods.setCpnMaster(newCpnMaster);
                this.cpnMasterGoodsRepository.save(cpnMasterGoods);
            });
        }

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
        // 기존 쿠폰 관련 상품 리스트 초기화
        if (existCpnMaster.getCpnMasterGoodsList() != null ) {
            existCpnMaster.getCpnMasterGoodsList().clear();
            existCpnMaster.getCpnMasterGoodsList().addAll(cpnMasterUpdateDto.getCpnMasterGoodsList());
        }

        // 변경된 쿠폰 상품 리스트 취득
        List<CpnMasterGoods> cpnMasterGoodsList = cpnMasterUpdateDto.getCpnMasterGoodsList();

        // 쿠폰 관련상품들 등록
        if (cpnMasterGoodsList != null && cpnMasterGoodsList.size() > 0 ) {

            cpnMasterGoodsList.forEach(cpnMasterGoods -> {
                cpnMasterGoods.setCpnMaster(existCpnMaster);
                this.cpnMasterGoodsRepository.save(cpnMasterGoods);
            });
        }

        // 데이터베이스 값 갱신
        this.cpnMasterRepository.save(existCpnMaster);

        // TODO cpnGoodsList 갱신? 잘 되는지 확인 후 안되면 list clear

        return existCpnMaster;
    }

}
