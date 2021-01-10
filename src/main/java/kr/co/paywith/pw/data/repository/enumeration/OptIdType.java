package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * The OrdrType enumeration.
 */
@Getter
public enum OptIdType implements EnumMapperType {
    OPT1("옵션1"), OPT2("옵션2"), OPT3("옵션3"), OPT4("옵션4"), OPT5("옵션5");
    
    private String title;
	   
	OptIdType(String title) {
    	this.title = title;
    }
	
	@Override
	public String getCode() {
		return name();
	}
}
