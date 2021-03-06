package kr.co.paywith.pw.data.repository.mbs.goodsOpt;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GoodsOptService {

  @Autowired
  GoodsOptRepository goodsOptRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;


  /**
   * 정보 등록
   */
  @Transactional
  public GoodsOpt create(GoodsOpt goodsOpt) {

    // 데이터베이스 값 갱신
    GoodsOpt newGoodsOpt = this.goodsOptRepository.save(goodsOpt);

    return newGoodsOpt;
  }


  /**
   * 정보 갱신
   */
  @Transactional
  public GoodsOpt update(GoodsOptUpdateDto goodsOptUpdateDto, GoodsOpt existGoodsOpt) {

    existGoodsOpt.getGoodsOptMasterList().clear();

    // TODO goodsOptMaster.id 가 있으므로 modelMapper 만으로 업데이트와 추가가 되어야 함. 혹시 안되면 로직 새로 구현
    // 입력값 대입
    this.modelMapper.map(goodsOptUpdateDto, existGoodsOpt);

    // 데이터베이스 값 갱신
    this.goodsOptRepository.save(existGoodsOpt);

    return existGoodsOpt;
  }

}
