package kr.co.paywith.pw.data.repository.mbs.goodsgrp;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GoodsGrpService {

    @Autowired
    private GoodsGrpRepository goodsGrpRepository;


    @Autowired
    private ModelMapper modelMapper;


    /**
     * 정보 등록
     */
    @Transactional
    public GoodsGrp create(GoodsGrp goodsGrp) {

        // 데이터베이스 값 갱신
        GoodsGrp newGoodsGrp = this.goodsGrpRepository.save(goodsGrp);

        return newGoodsGrp;
    }


    /**
     * 정보 갱신
     *
     * @param goodsGrpUpdateDto
     * @param existGoodsGrp
     * @return
     */
    @Transactional
    public GoodsGrp update(GoodsGrpUpdateDto goodsGrpUpdateDto, GoodsGrp existGoodsGrp) {

        // 입력값 대입
        this.modelMapper.map(goodsGrpUpdateDto, existGoodsGrp);

        // 데이터베이스 값 갱신
        this.goodsGrpRepository.save(existGoodsGrp);

        return existGoodsGrp;
    }

}
