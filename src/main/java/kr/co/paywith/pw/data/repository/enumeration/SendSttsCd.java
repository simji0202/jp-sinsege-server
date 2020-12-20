package kr.co.paywith.pw.data.repository.enumeration;

import lombok.Getter;

@Getter
public enum SendSttsCd implements EnumMapperType {
	RDY("대기"),
	ING("전송 중"),
	END("완료"),
	FAIL("실패"),
	EXPR("만료"); // 정상처리 되지 않았으나, 그 이후 다른 메시지가 있어 사용할 필요가 없게 된 경우

	private String title;

	SendSttsCd(String title) {
		this.title = title;
	}

	@Override
	public String getCode() {
		return name();
	}

}
