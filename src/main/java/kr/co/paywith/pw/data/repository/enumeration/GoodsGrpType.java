package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum GoodsGrpType implements EnumMapperType {
	GD("Goods");

	private String title;

	GoodsGrpType(String title) {
		this.title = title;
	}

	@Override
	public String getCode() {
		return name();
	}

}
