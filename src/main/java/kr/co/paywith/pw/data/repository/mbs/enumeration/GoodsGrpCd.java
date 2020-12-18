package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.Getter;

@Getter
public enum GoodsGrpCd implements EnumMapperType {
	GD("Goods");

	private String title;

	GoodsGrpCd(String title) {
		this.title = title;
	}

	@Override
	public String getCode() {
		return name();
	}

}
