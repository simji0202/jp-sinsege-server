package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * The OptionEtcType enumeration.
 */
@Getter
public enum InsttType implements EnumMapperType{
    KB("국민카드"), WOORI("우리카드");

	private String title;
	   
	InsttType(String title) {
    	this.title = title;
    }
	
	@Override
	public String getCode() {
		return name();
	}
}
