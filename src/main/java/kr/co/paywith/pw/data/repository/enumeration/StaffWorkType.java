package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

/**
 * The StaffWorkType enumeration.
 */
@Getter
public enum StaffWorkType implements EnumMapperType{
    ING("근무"), OUT("퇴사");

	private String title;
	   
	StaffWorkType(String title) {
    	this.title = title;
    }
	
	@Override
	public String getCode() {
		return name();
	}
}
