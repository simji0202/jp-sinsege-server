package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * The OptionEtcType enumeration.
 */
@Getter
public enum PayStatType implements EnumMapperType {
    WAIT("결제대기"), COMP("결제완료"), FAIL("실패"),CANCEL("취소");
    
	private String title;
	
	PayStatType(String title){
		this.title = title;
	}
	
	@Override
	public String getCode() {
		return name();
	}
}
