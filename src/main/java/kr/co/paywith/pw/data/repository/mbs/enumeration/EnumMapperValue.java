package kr.co.paywith.pw.data.repository.mbs.enumeration;

import lombok.ToString;

//@Getter
@ToString
public class EnumMapperValue {
	private String code;
	private String title;

	public EnumMapperValue(EnumMapperType enumMapperType) {
		this.code = enumMapperType.getCode();
		this.title = enumMapperType.getTitle();
	}

	public String getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

}
