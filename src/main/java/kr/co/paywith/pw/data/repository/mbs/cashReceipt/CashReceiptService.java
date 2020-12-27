package kr.co.paywith.pw.data.repository.mbs.cashReceipt;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CashReceiptService {

	@Autowired
	CashReceiptRepository cashReceiptRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public CashReceipt create(CashReceipt cashReceipt) {

		  // 데이터베이스 값 갱신
		  CashReceipt newCashReceipt = this.cashReceiptRepository.save(cashReceipt);

		  return newCashReceipt;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public CashReceipt update(CashReceiptUpdateDto cashReceiptUpdateDto, CashReceipt existCashReceipt) {

		  // 입력값 대입
		  this.modelMapper.map(cashReceiptUpdateDto, existCashReceipt);

		  // 데이터베이스 값 갱신
		  this.cashReceiptRepository.save(existCashReceipt);

		  return existCashReceipt;
	 }

}
