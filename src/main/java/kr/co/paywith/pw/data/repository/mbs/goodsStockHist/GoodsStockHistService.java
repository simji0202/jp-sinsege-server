package kr.co.paywith.pw.data.repository.mbs.goodsStockHist;


import javax.transaction.Transactional;
import kr.co.paywith.pw.data.repository.mbs.goodsStock.GoodsStock;
import kr.co.paywith.pw.data.repository.mbs.goodsStock.GoodsStockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class GoodsStockHistService {

    @Autowired
    private GoodsStockHistRepository goodsStockHistRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GoodsStockRepository goodsStockRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 정보 등록
     */
    @Transactional
    public GoodsStockHist create(GoodsStockHist goodsStockHist) {

        // 데이터베이스 값 갱신
        GoodsStockHist newGoodsStockHist = this.goodsStockHistRepository.save(goodsStockHist);

        // 재고 갱신
        GoodsStock goodsStock = goodsStockRepository.findById(newGoodsStockHist.getId()).get();
        goodsStock.setCnt(goodsStock.getCnt() + newGoodsStockHist.getCnt());
        goodsStock.setUpdateBy(goodsStockHist.getCreateBy());
        goodsStockRepository.save(goodsStock);

        return newGoodsStockHist;
    }


//    /**
//     * 정보 갱신
//     */
//    @Transactional
//    public GoodsStockHist update(GoodsStockHistUpdateDto goodsStockHistUpdateDto, GoodsStockHist existGoodsStockHist) {
//
//        // TODO goodsStockHistApplyList 갱신이 잘 되는지 확인 후 안되면 별도 처리 해야 함
//      //  existGoodsStockHist.getGoodsStockHistApplyList().clear();
//
//        // 입력값 대입
//        this.modelMapper.map(goodsStockHistUpdateDto, existGoodsStockHist);
//
//        // 데이터베이스 값 갱신
//        this.goodsStockHistRepository.save(existGoodsStockHist);
//
//        return existGoodsStockHist;
//    }

}
