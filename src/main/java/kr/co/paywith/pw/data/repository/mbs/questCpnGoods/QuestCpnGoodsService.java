package kr.co.paywith.pw.data.repository.mbs.questCpnGoods;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class QuestCpnGoodsService {

	@Autowired
	QuestCpnGoodsRepository questCpnGoodsRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public QuestCpnGoods create(QuestCpnGoods questCpnGoods) {

		  // 데이터베이스 값 갱신
		  QuestCpnGoods newQuestCpnGoods = this.questCpnGoodsRepository.save(questCpnGoods);

		  return newQuestCpnGoods;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public QuestCpnGoods update(QuestCpnGoodsUpdateDto questCpnGoodsUpdateDto, QuestCpnGoods existQuestCpnGoods) {

		  // 입력값 대입
		  this.modelMapper.map(questCpnGoodsUpdateDto, existQuestCpnGoods);

		  // 데이터베이스 값 갱신
		  this.questCpnGoodsRepository.save(existQuestCpnGoods);

		  return existQuestCpnGoods;
	 }

}
