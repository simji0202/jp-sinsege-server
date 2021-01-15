package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * The ReserveOrdrType enumeration.
 */
@Getter
public enum ReserveRefundType implements EnumMapperType {
    A(100,"1일전"), B(80,"1시간전");
    
	private int    ratio;
    private String title;
	   
	ReserveRefundType(int ratio, String title) {
		this.ratio = ratio;
		this.title = title;
    }
	
	@Override
	public String getCode() {
		return name();
	}
}
