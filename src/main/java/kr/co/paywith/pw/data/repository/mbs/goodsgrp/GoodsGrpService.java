package kr.co.paywith.pw.data.repository.mbs.goodsgrp;


import com.querydsl.core.BooleanBuilder;
import java.util.concurrent.atomic.AtomicInteger;
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

        arrangeSort(newGoodsGrp);

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

        arrangeSort(existGoodsGrp);
        return existGoodsGrp;
    }

    /**
     * sort 가 같거나 큰 아이템 재정렬
     */
    private void arrangeSort(GoodsGrp goodsGrp) {

        BooleanBuilder bb = new BooleanBuilder();
        QGoodsGrp qGoodsGrp = QGoodsGrp.goodsGrp;
        bb.and(qGoodsGrp.brand.id.eq(goodsGrp.getBrand().getId()));
        if (goodsGrp.getParentGoodsGrpSn() != null) {
            bb.and(qGoodsGrp.parentGoodsGrpSn.eq(goodsGrp.getParentGoodsGrpSn()));
        } else {
            bb.and(qGoodsGrp.parentGoodsGrpSn.isNull());
        }
        bb.and(qGoodsGrp.id.ne(goodsGrp.getId()));
        if (goodsGrp.getSort() != null) {
            bb.and(qGoodsGrp.sort.goe(goodsGrp.getSort()).or(qGoodsGrp.sort.isNull()));
        } else {
            bb.and(qGoodsGrp.sort.isNull());
        }
        AtomicInteger sort = new AtomicInteger(goodsGrp.getSort());
        goodsGrpRepository.findAll(bb).forEach(goodsGrp1 -> {
            goodsGrp1.setSort(sort.incrementAndGet());
            goodsGrpRepository.save(goodsGrp1);
        });
    }
}
