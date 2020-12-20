package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum UserAppPatchType {
	UPD("Update All");

	private String title;

	UserAppPatchType(String title) {
		this.title = title;
	}
}
