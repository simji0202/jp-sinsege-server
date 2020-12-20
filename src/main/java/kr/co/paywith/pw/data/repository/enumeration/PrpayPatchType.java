package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum PrpayPatchType {
	REG("카드 등록"),
	REQ("신규 카드 발급"),
	UPD("업데이트");

	private String title;

	PrpayPatchType(String title) {
		this.title = title;
	}
}
