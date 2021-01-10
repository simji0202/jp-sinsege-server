package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * The ReserveOrdrType enumeration.
 */
@Getter
public enum ReserveOrdrType implements EnumMapperType {
    DIRECT("즉시"), RSRV30("예약 30분후"), RSRV60("예약 60분후"), RSRV120("예약 120분후");
    
    private String title;
	   
	ReserveOrdrType(String title) {
		this.title = title;
    }
	
	@Override
	public String getCode() {
		return name();
	}
}
