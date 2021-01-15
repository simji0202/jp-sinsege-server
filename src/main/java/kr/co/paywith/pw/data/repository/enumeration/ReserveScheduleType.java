package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * The SeatType enumeration.
 */
@Getter
public enum ReserveScheduleType implements EnumMapperType{
	WEEKDAY("평일"), WEEKEND("주말"), HOLIDAY("공휴일"), MON("월요일"), TUE("화요일"), WED("수요일"), THU("목요일"),
	FRI("금요일"), SAT("토요일"), SUN("일요일");

	private String title;
	   
	ReserveScheduleType(String title) {
    	this.title = title;
    }
	
	@Override
	public String getCode() {
		return name();
	}
}
