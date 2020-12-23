package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum PointCutTypeCd implements EnumMapperType {
	F("올림"),
	R("반올림"),
	C("버림");

	private String title;

	PointCutTypeCd(String title) {
		this.title = title;
	}

	@Override
	public String getCode() {
		return name();
	}

}
