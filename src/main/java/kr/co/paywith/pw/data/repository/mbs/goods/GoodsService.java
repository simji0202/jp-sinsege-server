package kr.co.paywith.pw.data.repository.mbs.goods;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 정보 등록
     */
    @Transactional
    public Goods create(Goods goods) {

        // 데이터베이스 값 갱신
        Goods newGoods = this.goodsRepository.save(goods);

        return newGoods;
    }


    /**
     * 정보 갱신
     */
    @Transactional
    public Goods update(GoodsUpdateDto goodsUpdateDto, Goods existGoods) {

        // TODO goodsApplyList 갱신이 잘 되는지 확인 후 안되면 별도 처리 해야 함
        existGoods.getGoodsApplyList().clear();

        // 입력값 대입
        this.modelMapper.map(goodsUpdateDto, existGoods);

        // 데이터베이스 값 갱신
        this.goodsRepository.save(existGoods);

        return existGoods;
    }

}
