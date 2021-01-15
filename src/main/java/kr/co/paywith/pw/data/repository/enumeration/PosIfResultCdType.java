package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * The SrStatType enumeration.
 */
@Getter
public enum PosIfResultCdType implements EnumMapperType {
    SUCCESS("성공"), ERROR("에러");
    
    private String title;
	   
	PosIfResultCdType(String title) {
    	this.title = title;
    }
	
	@Override
	public String getCode() {
		return name();
	}
}
