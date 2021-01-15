package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * The SeatType enumeration.
 */
@Getter
public enum SeatType implements EnumMapperType {
    ROOM("방"), TABLE("테이블(실내)"), TABLE_OUT("테이블(실외)");

	private String title;
	   
	SeatType(String title) {
    	this.title = title;
    }

	@Override
	public String getCode() {
		return name();
	}
}
