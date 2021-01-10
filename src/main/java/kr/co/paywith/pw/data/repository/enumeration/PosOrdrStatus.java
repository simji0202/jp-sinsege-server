package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * The OrdrStatus enumeration.
 */
@Getter
public enum PosOrdrStatus implements EnumMapperType {
	accepted("결제완료", OrdrStatus.ACCEPT), 
	cooking("조리시작", OrdrStatus.ACCEPT),
	readied("상품준비완료", OrdrStatus.READY), 
	delivering("배송중", OrdrStatus.DELIV), 
	completed("배송완료", OrdrStatus.DELIVCOMP),
	canceled("주문취소", OrdrStatus.CANCEL);
    
    private String title;
    private OrdrStatus ordr;
    
	PosOrdrStatus(String title, OrdrStatus ordr) {
		this.ordr = ordr;
    	this.title = title;
    }
	@Override
	public String getCode() {
		return name();
	}

	
	
}
